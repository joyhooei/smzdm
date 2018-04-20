package com.ppjun.android.smzdm.mvp.ui.activity.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.component.DaggerAppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.paginate.Paginate
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseFragView
import com.ppjun.android.smzdm.di.component.DaggerMainComponent
import com.ppjun.android.smzdm.di.module.MainModule
import com.ppjun.android.smzdm.mvp.contract.MainContract
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.presenter.MainPresenter
import com.ppjun.android.smzdm.mvp.ui.holder.MainHolder
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.main_rv.view.*

import javax.inject.Inject

class MainFragment : BaseFragView<MainPresenter>(), MainContract.View {
    override fun launchActivity(intent: Intent?) {

    }

    override fun killMyself() {
    }



    @Inject
    lateinit var mRxPermissions: RxPermissions

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var mAdapter: DefaultAdapter<Row>

    var rv: RecyclerView? = null
    var swipe:SwipeRefreshLayout ?=null
    var mPaginate: Paginate? = null
    var isLoadingMore = false


    override fun getTheActivity(): Activity {
        return activity!!
    }

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)

    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        rv?.adapter = mAdapter

        initPaginate()
    }

    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    mPresenter.requestMainList(false)
                }

                override fun isLoading(): Boolean {
                    return isLoadingMore
                }

                override fun hasLoadedAllItems(): Boolean {
                    return false
                }
            }

            mPaginate = Paginate.with(rv, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build()
            mPaginate?.setHasMoreDataToLoad(false)
        }
    }

    private fun initRecyclerView() {
        rv = view?.mainViewRv
        swipe =view?.mainSwipe
        swipe?.setOnRefreshListener {
            mPresenter.requestMainList(true)
        }

        ArmsUtils.configRecyclerView(rv, mLayoutManager)

    }


    override fun setData(data: Any?) {

    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        LogUtils.debugInfo("initview")
        return inflater!!.inflate(R.layout.main_rv, container, false)


    }

    override fun startLoadMore() {
        isLoadingMore = true
    }

    override fun endLoadMore() {
        isLoadingMore = false
    }

    override fun getRxPermission(): RxPermissions {
        return RxPermissions(getTheActivity())
    }

    override fun showLoading() {
        swipe?.isRefreshing=true

    }


    override fun hideLoading() {
        swipe?.isRefreshing=false
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