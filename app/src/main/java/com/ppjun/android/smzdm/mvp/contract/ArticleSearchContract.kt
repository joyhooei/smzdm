package com.ppjun.android.smzdm.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleData
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleList
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleSearch
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

class ArticleSearchContract {
    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun getTheActivity(): Activity
        fun getRxPermission(): RxPermissions
        fun hasLoadedAllItems(isLoadedAll:Boolean)
        fun moveRVToTop()
        fun setEmptyView()
    }

    interface Model : IModel {
        fun articleList(keyword: String, page: Int, boolean: Boolean): Observable<Response<ArticleSearch<ArticleData>>>

    }
}