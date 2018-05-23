package com.ppjun.android.smzdm.mvp.ui.activity.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.paginate.Paginate
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseFragView
import com.ppjun.android.smzdm.di.component.DaggerMainComponent
import com.ppjun.android.smzdm.di.module.MainModule
import com.ppjun.android.smzdm.mvp.contract.MainContract
import com.ppjun.android.smzdm.mvp.model.entity.main.MainBanner
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.presenter.MainPresenter
import com.ppjun.android.smzdm.mvp.ui.viewbinder.BannerDelegateAdapter
import com.ppjun.android.smzdm.mvp.ui.viewbinder.MainDelegateAdapter
import com.ppjun.android.smzdm.mvp.ui.widget.PPJunRecyclerView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.main_rv.view.*
import javax.inject.Inject

class MainFragment : BaseFragView<MainPresenter>(), MainContract.View {

    @Inject
    lateinit var mRxPermissions: RxPermissions
    @Inject
    lateinit var mLayoutManager: VirtualLayoutManager
    @Inject
    lateinit var delegateAdapter: DelegateAdapter

    var mainDelegateAdapter:MainDelegateAdapter ?=null

    var rv: PPJunRecyclerView? = null
    var swipe: SwipeRefreshLayout? = null
    var mPaginate: Paginate? = null
    var isLoadingMore = false

    var adapters = ArrayList<DelegateAdapter.Adapter<*>>()

    override fun setMainList(isRefresh: Boolean, bannerList: List<Row>) {
//
        mainDelegateAdapter=MainDelegateAdapter(bannerList)
        adapters.add(mainDelegateAdapter!!)
        delegateAdapter.setAdapters(adapters)
        mainDelegateAdapter?.notifyDataSetChanged()
        initPaginate()
    }


    override fun setBanner(bannerList: ArrayList<MainBanner>) {
        adapters.clear()
        delegateAdapter.notifyDataSetChanged()
        adapters.add(BannerDelegateAdapter(bannerList))
        mPresenter.requestMainList(true)
    }

    override fun launchActivity(intent: Intent?) {

    }

    override fun killMyself() {
    }


    override fun getTheActivity(): Activity {
        return requireNotNull(activity)
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
                    .setLoadingTriggerThreshold(2)
                    .build()
            mPaginate?.setHasMoreDataToLoad(false)
        }
    }

    private fun initRecyclerView() {
        rv = view?.mainViewRv
        swipe = view?.mainSwipe
        swipe?.setOnRefreshListener {
              mPresenter.requestMainBanner()
        }
        //  mPresenter.requestMainList(true)


        rv?.layoutManager = mLayoutManager
        rv?.adapter = delegateAdapter
        mPresenter.requestMainBanner()
    }


    override fun setData(data: Any?) {


    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return requireNotNull(inflater).inflate(R.layout.main_rv, container, false)
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
        swipe?.isRefreshing = true

    }


    override fun hideLoading() {
        swipe?.isRefreshing = false
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