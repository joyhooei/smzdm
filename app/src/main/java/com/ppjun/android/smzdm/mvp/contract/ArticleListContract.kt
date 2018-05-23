package com.ppjun.android.smzdm.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleList
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

class ArticleListContract {


    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun getTheActivity(): Activity
        fun getRxPermission(): RxPermissions
    }

    interface Model : IModel {
        fun articleList(page: Int, boolean: Boolean): Observable<ArticleList>
    }
}