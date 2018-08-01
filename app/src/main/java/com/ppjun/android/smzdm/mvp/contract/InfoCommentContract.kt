package com.ppjun.android.smzdm.mvp.contract

import android.app.Activity
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.ppjun.android.smzdm.mvp.model.entity.main.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

class InfoCommentContract {
    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun setEmptyView()
        fun toastMessage(message: String?)
        fun getTheActivity(): Activity
        fun getRxPermission(): RxPermissions
        fun hasLoadedAllItems(isLoadedAll:Boolean)

    }
    interface Model : IModel {
        fun commentList(articleId: String, type: String ,page: Int): Observable<Response<Data<InfoComment>>>
    }

}