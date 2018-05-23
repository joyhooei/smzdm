package com.ppjun.android.smzdm.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.ppjun.android.smzdm.mvp.contract.NewsInfoContract
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.NewsInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject

@ActivityScope
class NewsInfoPresenter @Inject constructor(model: NewsInfoContract.Model, view: NewsInfoContract.View
) : BasePresenter<NewsInfoContract.Model, NewsInfoContract.View>(model, view) {
    @Inject
    @JvmField
    var mErrorHandler: RxErrorHandler? = null
    @Inject
    @JvmField
    var mAppManager: AppManager? = null
    @Inject
    @JvmField
    var mApplication: Application? = null


    private var offset = 20
    private var isFirst = true
    private var preEndIndex = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreate(){

    }
    fun requestNewsInfo(id:String){

        val  pullToRefresh=true


        mModel.newsInfo(id, pullToRefresh)
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
                .subscribe(object : ErrorHandleSubscriber<Response<NewsInfo>>(mErrorHandler) {
                    override fun onNext(result: Response<NewsInfo>){
                        mRootView.showInfo(result.data)
                    }

                })
    }



}