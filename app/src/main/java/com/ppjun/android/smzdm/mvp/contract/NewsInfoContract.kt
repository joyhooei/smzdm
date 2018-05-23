package com.ppjun.android.smzdm.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.NewsInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable


interface NewsInfoContract {

    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun getTheActivity(): Activity
        fun getRxPermission(): RxPermissions
        fun showInfo(info: NewsInfo)
    }

    interface Model : IModel {
        fun newsInfo(id: String, update: Boolean): Observable<Response<NewsInfo>>
    }
}