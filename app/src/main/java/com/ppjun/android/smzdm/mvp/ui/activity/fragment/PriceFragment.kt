package com.ppjun.android.smzdm.mvp.ui.activity.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.paginate.Paginate
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseFragView
import com.ppjun.android.smzdm.di.component.DaggerPriceListComponent
import com.ppjun.android.smzdm.di.module.PriceListModule
import com.ppjun.android.smzdm.mvp.contract.PriceContract
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import com.ppjun.android.smzdm.mvp.presenter.PriceListPresenter
import com.ppjun.android.smzdm.mvp.ui.adapter.BottomLoadingCreator
import com.ppjun.android.smzdm.mvp.ui.widget.PPJunRecyclerView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.main_rv.view.*
import javax.inject.Inject

class PriceFragment : BaseFragView<PriceListPresenter>(), PriceContract.View {

    @Inject
    lateinit var mRxPermissions: RxPermissions

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var mAdapter: DefaultAdapter<PriceRow>

    var rv: PPJunRecyclerView? = null
    var swipe: SwipeRefreshLayout? = null
    var mPaginate: Paginate? = null
    var isLoadingMore = false


    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        rv?.adapter = mAdapter
        initPaginate()

    }

    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    mPresenter.requestPriceList(false)
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
                    .setLoadingListItemCreator(BottomLoadingCreator())
                    .build()
            mPaginate?.setHasMoreDataToLoad(false)
        }
    }

    private fun initRecyclerView() {
        rv = view?.mainViewRv
        val toTop = view?.mainToTop
        toTop?.visibility=View.VISIBLE
        toTop?.setOnClickListener {
            rv?.scrollToPosition(0)
        }
        swipe = view?.mainSwipe
        swipe?.setOnRefreshListener {
            mPresenter.requestPriceList(true)
        }
        mPresenter.requestPriceList(true)
        ArmsUtils.configRecyclerView(rv, mLayoutManager)

    }



    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater!!.inflate(R.layout.main_rv, container, false)
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerPriceListComponent.builder()
                .appComponent(appComponent)
                .priceListModule(PriceListModule(this))
                .build()
                .inject(this)
    }

    override fun setData(data: Any?) {

    }

    override fun startLoadMore() {
        isLoadingMore = true
    }

    override fun endLoadMore() {
        isLoadingMore = false
    }

    override fun getTheActivity(): Activity {
        return activity!!
    }

    override fun getRxPermission(): RxPermissions {
        return mRxPermissions
    }

    override fun showLoading() {
        swipe?.isRefreshing = true
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
        swipe?.isRefreshing = false
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun onDestroy() {
        DefaultAdapter.releaseAllHolder(rv)
        super.onDestroy()
        this.mPaginate = null
    }
}