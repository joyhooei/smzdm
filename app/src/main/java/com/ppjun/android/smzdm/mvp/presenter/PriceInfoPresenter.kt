package com.ppjun.android.smzdm.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BaseModel
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.ppjun.android.smzdm.mvp.contract.PriceContract
import com.ppjun.android.smzdm.mvp.contract.PriceInfoContract
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleData
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleList
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject


@ActivityScope
class PriceInfoPresenter @Inject constructor(model: PriceInfoContract.Model,view:PriceInfoContract.View):
        BasePresenter<PriceInfoContract.Model,PriceInfoContract.View>(model,view)
{



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


    fun requestPriceInfo(id:String){

       val  pullToRefresh=true


        mModel.priceInfo(id, pullToRefresh)
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
                .subscribe(object : ErrorHandleSubscriber<Response<PriceInfo>>(mErrorHandler) {
                    override fun onNext(result: Response<PriceInfo>){
                        mRootView.showInfo(result.data)
                    }

                })
    }

}