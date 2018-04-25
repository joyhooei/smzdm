package com.ppjun.android.smzdm.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.ppjun.android.smzdm.mvp.model.entity.main.MainList
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

interface MainContract {


    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun getTheActivity(): Activity
        fun getRxPermission(): RxPermissions
    }

    interface Model : IModel {
        fun getMain(page: Int, boolean: Boolean): Observable<MainList>
    }
}