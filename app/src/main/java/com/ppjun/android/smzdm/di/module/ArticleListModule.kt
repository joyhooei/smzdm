package com.ppjun.android.smzdm.di.module

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.mvp.contract.ArticleListContract
import com.ppjun.android.smzdm.mvp.contract.MainContract
import com.ppjun.android.smzdm.mvp.model.ArticleListModel
import com.ppjun.android.smzdm.mvp.model.MainModel
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleData
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.adapter.ArticleListAdapter
import com.ppjun.android.smzdm.mvp.ui.adapter.MainAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides


@Module
class ArticleListModule constructor(var view: ArticleListContract.View) {


    @FragmentScope
    @Provides
    fun provideArticleListView(): ArticleListContract.View= view


    @FragmentScope
    @Provides
    fun provideArticleListModel(model: ArticleListModel):ArticleListContract.Model = model

    @FragmentScope
    @Provides
    fun provideRxPermissions(): RxPermissions = RxPermissions(view.getTheActivity())


    @FragmentScope
    @Provides
    fun provideLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(view.getTheActivity()
            , LinearLayoutManager.VERTICAL, false)

    @FragmentScope
    @Provides
    fun provideRowList() :ArrayList<ArticleData> = ArrayList()


    @FragmentScope
    @Provides
    fun provideRowAdapter(list: ArrayList<ArticleData>): DefaultAdapter<ArticleData> = ArticleListAdapter(list)
}