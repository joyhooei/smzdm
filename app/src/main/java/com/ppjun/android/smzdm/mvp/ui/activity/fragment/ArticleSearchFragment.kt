package com.ppjun.android.smzdm.mvp.ui.activity.fragment

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.component.DaggerAppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.paginate.Paginate
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseFragView
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.di.component.DaggerArticleSearchComponent
import com.ppjun.android.smzdm.di.module.ArticleSearchModule
import com.ppjun.android.smzdm.mvp.contract.ArticleSearchContract
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleData
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import com.ppjun.android.smzdm.mvp.presenter.ArticleSearchPresenter
import com.ppjun.android.smzdm.mvp.ui.activity.SearchResultActivity
import com.ppjun.android.smzdm.mvp.ui.adapter.BottomLoadingCreator
import com.ppjun.android.smzdm.mvp.ui.widget.PPJunRecyclerView
import com.ppjun.android.smzdm.mvp.viewModel.KeywordModel
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.main_rv.view.*
import javax.inject.Inject

class ArticleSearchFragment : BaseFragView<ArticleSearchPresenter>(), ArticleSearchContract.View {
    @Inject
    lateinit var mRxPermissions: RxPermissions

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var mAdapter: DefaultAdapter<ArticleData>

    var rv: PPJunRecyclerView? = null
    var swipe: SwipeRefreshLayout? = null
    var mPaginate: Paginate? = null
    var isLoadingMore = false
    var mKeyword: String = ""
    var isLoadedAll = false
    lateinit var keywordModel: KeywordModel
    lateinit var SearchUI: SearchResultActivity


    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        rv?.adapter = mAdapter
        initPaginate()
        keywordModel.getKeyWord().observe(this, Observer<String> {
            mKeyword= requireNotNull(it)

            mPresenter.requestArticleList(mKeyword, true)
        })

        SearchUI.setOnArticleSearchListener(object : SearchResultActivity.OnArticleSearchListener {
            override fun search(keyword: String) {
                Log.d("keyword=", "articlekeyword=" + keyword)
                mKeyword = keyword
                if (mKeyword.isNotEmpty())
                    mPresenter.requestArticleList(mKeyword, true)
                closeKeyBoard(activity!!)
            }

        })
    }

    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    if (mKeyword.isNotEmpty() && mAdapter.infos.size > 3)
                        mPresenter.requestArticleList(mKeyword, false)
                }

                override fun isLoading(): Boolean {

                    return isLoadingMore
                }

                override fun hasLoadedAllItems(): Boolean {

                    return isLoadedAll
                }
            }
            //0个到底了就触发
            mPaginate = Paginate.with(rv, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .setLoadingListItemCreator(BottomLoadingCreator())
                    .build()
            mPaginate?.setHasMoreDataToLoad(false)
        }
    }

    private fun initRecyclerView() {
        rv = view?.mainViewRv
        swipe = view?.mainSwipe
        SearchUI = activity as SearchResultActivity
        keywordModel = ViewModelProviders.of(activity as SearchResultActivity).get(KeywordModel::class.java)

        swipe?.setOnRefreshListener {
            if (mKeyword.isNotEmpty())
                mPresenter.requestArticleList(mKeyword, true)
        }
        ArmsUtils.configRecyclerView(rv, mLayoutManager)

    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.main_rv, container, false)

    }

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        DaggerArticleSearchComponent.builder()
                .appComponent(appComponent)
                .articleSearchModule(ArticleSearchModule(this))
                .build().inject(this)
    }

    override fun setData(data: Any?) {
    }

    override fun startLoadMore() {
        isLoadingMore = true
    }

    override fun endLoadMore() {
        isLoadingMore = false
        mPaginate?.setHasMoreDataToLoad(false)
    }

    override fun getTheActivity(): Activity {
        return requireNotNull(activity)
    }

    override fun getRxPermission(): RxPermissions {
        return mRxPermissions
    }

    override fun hasLoadedAllItems(isLoadedAll: Boolean) {
        this.isLoadedAll = isLoadedAll

    }

    override fun moveRVToTop() {
        rv?.smoothScrollToPosition(0)
    }

    override fun setEmptyView() {
        rv?.setDefaultEmptyView()
        mPaginate?.setHasMoreDataToLoad(false)
    }

    override fun showLoading() {
        swipe?.isRefreshing = true
    }

    override fun launchActivity(intent: Intent?) {
    }

    override fun hideLoading() {
        swipe?.isRefreshing = false
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String?) {
        ArmsUtils.snackbarText(message)
    }

    override fun onDestroy() {
        DefaultAdapter.releaseAllHolder(rv)
        super.onDestroy()
        this.mPaginate = null
    }
}