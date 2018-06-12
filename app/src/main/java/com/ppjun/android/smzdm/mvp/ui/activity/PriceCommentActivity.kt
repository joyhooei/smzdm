package com.ppjun.android.smzdm.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.paginate.Paginate
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.app.utils.StatusUtils
import com.ppjun.android.smzdm.di.component.DaggerPriceCommentComponent
import com.ppjun.android.smzdm.di.module.PriceCommentModule
import com.ppjun.android.smzdm.mvp.contract.InfoCommentContract
import com.ppjun.android.smzdm.mvp.model.entity.main.InfoComment
import com.ppjun.android.smzdm.mvp.presenter.InfoCommentPresenter
import com.ppjun.android.smzdm.mvp.ui.widget.PPJunRecyclerView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.main_rv.*
import javax.inject.Inject

class PriceCommentActivity : BaseUI<InfoCommentPresenter>(), InfoCommentContract.View {

    @Inject
    lateinit var mRxPermissions: RxPermissions
    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    @Inject
    lateinit var mAdapter: DefaultAdapter<InfoComment>

    var rv: PPJunRecyclerView? = null
    var swipe: SwipeRefreshLayout? = null
    var mPaginate: Paginate? = null
    var isLoadingMore = false
    var isLoadedAll = false
    lateinit var mArticleId: String
    lateinit var mType: String

    override fun startLoadMore() {
        isLoadingMore = true

    }

    override fun endLoadMore() {
        isLoadingMore = false
        mPaginate?.setHasMoreDataToLoad(false)


    }

    override fun setEmptyView() {
        mainToTop.visibility = View.GONE
        rv?.setDefaultEmptyView()
        mPaginate?.setHasMoreDataToLoad(false)
    }

    override fun getTheActivity(): Activity {
        return this
    }

    override fun getRxPermission(): RxPermissions {
        return mRxPermissions
    }

    override fun hasLoadedAllItems(isLoadedAll: Boolean) {
        this.isLoadedAll = isLoadedAll
    }

    override fun showLoading() {
        swipe?.isRefreshing = true
    }

    override fun launchActivity(intent: Intent?) {
    }

    override fun hideLoading() {

        swipe?.isRefreshing = false
        if (mainToTop.visibility == View.GONE&&mPresenter.isEmptyView.not()) {
            toolbarTitle.text = String.format(getString(R.string.comment_s), intent.getStringExtra(Constant.ARTICLE_COUNT))
            mainToTop.visibility = View.VISIBLE
            mainToTop.setOnClickListener {
                rv?.scrollToPosition(0)
            }
        }
    }

    override fun killMyself() {

    }

    override fun showMessage(message: String?) {
        ArmsUtils.snackbarText(message)
    }

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerPriceCommentComponent.builder()
                .appComponent(appComponent)
                .priceCommentModule(PriceCommentModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.main_rv
    }

    override fun initData(savedInstanceState: Bundle?) {

        toolSearch.visibility = View.GONE
        toolbar.visibility = View.VISIBLE
        toolbar.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0)
        toolbarTitle.visibility = View.VISIBLE

        mArticleId = intent.getStringExtra(Constant.ARTICLE_ID)
        mType = intent.getStringExtra(Constant.ARTICLE_TYPE)
        initRecyclerView()
        rv?.adapter = mAdapter
        initPaginate()
        mPresenter.requestCommentList(mArticleId, mType, true)

    }

    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    if (mAdapter.infos.size > 4)
                        mPresenter.requestCommentList(mArticleId, mType, false)
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
                    .build()
            mPaginate?.setHasMoreDataToLoad(false)
        }
    }

    private fun initRecyclerView() {
        rv = mainViewRv
        swipe = mainSwipe
        swipe?.setOnRefreshListener {
            mPresenter.requestCommentList(mArticleId, mType, true)
        }

        ArmsUtils.configRecyclerView(rv, mLayoutManager)

    }

    override fun onDestroy() {
        DefaultAdapter.releaseAllHolder(rv)
        super.onDestroy()
        this.mPaginate = null
    }
}