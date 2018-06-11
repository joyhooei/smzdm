package com.ppjun.android.smzdm.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.alibaba.android.vlayout.DelegateAdapter
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.contract.MainContract
import com.ppjun.android.smzdm.mvp.model.entity.main.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject


@FragmentScope
class MainPresenter @Inject constructor(model: MainContract.Model, view: MainContract.View) : BasePresenter<MainContract.Model, MainContract.View>(model, view) {
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
    var mAdapter:DelegateAdapter ?= null
    @Inject
    @JvmField
    var mRow: ArrayList<Row>? = null
    private var offset = 20
    private var isFirst = true
    private var preEndIndex = 0


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreate() {

    }

    fun requestMainBanner() {


        mModel.getMainBanner(offset, true)

                .subscribeOn(Schedulers.io())
                .retryWhen(
                        RetryWithDelay(3, 2)
                )
                .doOnSubscribe { disposible ->

                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {

                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<Response<Data<MainBanner>>>(mErrorHandler) {
                    override fun onNext(result: Response<Data<MainBanner>>) {
                      mRootView.setBanner(result.data.rows)
                    }
                })


    }

    fun requestMainList(pullToRefresh: Boolean) {

        if (pullToRefresh) {
            offset = 0
        }

        var isEvictCache = pullToRefresh
        if (pullToRefresh && isFirst) {
            isFirst = false
            isEvictCache = false
        }



        mModel.getMain(offset, isEvictCache)
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
                .subscribe(object : ErrorHandleSubscriber<MainList>(mErrorHandler) {
                    override fun onNext(result: MainList) {
                        offset += 20
                        if (pullToRefresh) {
                            mRow?.clear()
                        }
                        preEndIndex = requireNotNull(mRow?.size)
                        mRow?.addAll(result.data.rows)
                        if (pullToRefresh) {
                           // mAdapter?.notifyDataSetChanged()
                            mRootView.setMainList(true,result.data.rows)
                        } else {
                          //  mAdapter?.notifyItemRangeInserted(preEndIndex, result.data.rows.size)
                            mRootView.setMainList(false,result.data.rows)
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

    fun toSystemSettingUI() {
        val localIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        localIntent.data = Uri.fromParts(Constant.PACKAGE, mRootView.getTheActivity().packageName, null)
        mRootView.getTheActivity().startActivity(localIntent)
    }
}