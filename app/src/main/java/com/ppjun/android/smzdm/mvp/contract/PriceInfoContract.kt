package com.ppjun.android.smzdm.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceList
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

class PriceInfoContract {

    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun getTheActivity(): Activity
        fun getRxPermission(): RxPermissions
        fun showInfo(info:PriceInfo)
    }

    interface Model : IModel {
        fun priceInfo(id: String, boolean: Boolean): Observable<Response<PriceInfo>>
    }
}