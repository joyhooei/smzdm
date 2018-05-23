package com.ppjun.android.smzdm.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceList
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

class PriceContract {

    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun getTheActivity(): Activity
        fun getRxPermission(): RxPermissions
    }

    interface Model : IModel {
        fun priceList(page: Int, boolean: Boolean): Observable<PriceList>
    }
}