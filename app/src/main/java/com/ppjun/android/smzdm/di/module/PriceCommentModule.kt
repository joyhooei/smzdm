package com.ppjun.android.smzdm.di.module

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.mvp.contract.ArticleSearchContract
import com.ppjun.android.smzdm.mvp.contract.InfoCommentContract
import com.ppjun.android.smzdm.mvp.model.ArticleSearchModel
import com.ppjun.android.smzdm.mvp.model.InfoCommentModel
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleData
import com.ppjun.android.smzdm.mvp.model.entity.main.InfoComment
import com.ppjun.android.smzdm.mvp.ui.adapter.ArticleListAdapter
import com.ppjun.android.smzdm.mvp.ui.adapter.CommentAdapter
import com.ppjun.android.smzdm.mvp.ui.holder.CommentHolder
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides


@Module
class PriceCommentModule constructor(var view:InfoCommentContract.View) {



    @ActivityScope
    @Provides
    fun provideInfoCommentView(): InfoCommentContract.View = view

    @ActivityScope
    @Provides
    fun provideInfoCommentModel(model: InfoCommentModel): InfoCommentContract.Model = model


    @ActivityScope
    @Provides
    fun provideRxPermissions(): RxPermissions = RxPermissions(view.getTheActivity())


    @ActivityScope
    @Provides
    fun provideLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(view.getTheActivity()
            , LinearLayoutManager.VERTICAL, false)


    @ActivityScope
    @Provides
    fun provideInfoComment(): ArrayList<InfoComment> = ArrayList()

    @ActivityScope
    @Provides
    fun provideInfoCommentAdapter(list: ArrayList<InfoComment>): DefaultAdapter<InfoComment> = CommentAdapter(list)

}