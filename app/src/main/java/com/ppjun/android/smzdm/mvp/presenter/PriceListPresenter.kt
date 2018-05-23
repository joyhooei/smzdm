package com.ppjun.android.smzdm.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.ppjun.android.smzdm.mvp.contract.PriceContract
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceList
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import com.ppjun.android.smzdm.mvp.model.entity.main.Row

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject


@FragmentScope
class PriceListPresenter @Inject constructor(model:PriceContract.Model,view :PriceContract.View)
    :BasePresenter<PriceContract.Model,PriceContract.View>(model,view){


    @Inject
    @JvmField
    var mErrorHandler: RxErrorHandler? = null
    @Inject
    @JvmField
    var mAppManager: AppManager? = null
    @Inject
    @JvmField
    var mApplication: Application? = null
    @Inject
    @JvmField
    var mAdapter: DefaultAdapter<PriceRow>? = null
    @Inject
    @JvmField
    var mRow: ArrayList<PriceRow>? = null
    private var offset = 20
    private var isFirst = true
    private var preEndIndex = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
     fun onCreate(){
       //  requestPriceList(true)
     }


    fun requestPriceList(pullToRefresh: Boolean){


        if (pullToRefresh) {
            offset = 0
        }

        var isEvictCache = pullToRefresh
        if (pullToRefresh && isFirst) {
            isFirst = false
            isEvictCache = false
        }
        mModel.priceList(offset, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(
                        RetryWithDelay(3, 2)
                )
                .doOnSubscribe { disposible ->
                    if (pullToRefresh) {
                        mRootView.showLoading()
                    } else {
                        mRootView.startLoadMore()
                    }
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (pullToRefresh) {
                        mRootView.hideLoading()
                    } else {
                        mRootView.endLoadMore()
                    }
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<PriceList>(mErrorHandler) {
                    override fun onNext(result: PriceList) {
                        offset += 20
                        if (pullToRefresh) {
                            mRow?.clear()
                        }
                        preEndIndex = requireNotNull(mRow?.size)
                        mRow?.addAll(result.data.rows)
                        if (pullToRefresh) {
                            mAdapter?.notifyDataSetChanged()
                        } else {
                            mAdapter?.notifyItemRangeInserted(preEndIndex, result.data.rows.size)
                        }

                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mAdapter = null
        this.mRow = null
        this.mErrorHandler = null
        this.mAppManager = null
        this.mApplication = null
    }

}