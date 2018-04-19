package com.ppjun.android.smzdm.app.utils

import com.jess.arms.mvp.IView
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class RxUtils private constructor() {


    fun <T> applySchedulers(view:IView):ObservableTransformer<T,T>{
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .doOnSubscribe { view.showLoading() }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { view.hideLoading() }
                    .compose(RxLifecycleUtils.bindToLifecycle(view))
        }
    }
}