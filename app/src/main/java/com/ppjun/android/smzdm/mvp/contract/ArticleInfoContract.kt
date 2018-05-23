package com.ppjun.android.smzdm.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

class ArticleInfoContract {
    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun getTheActivity(): Activity
        fun getRxPermission(): RxPermissions
        fun showInfo(info: ArticleInfo)
    }

    interface Model : IModel {
        fun articleInfo(id: String, update: Boolean): Observable<Response<ArticleInfo>>
    }
}