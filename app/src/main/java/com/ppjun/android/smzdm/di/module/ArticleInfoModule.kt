package com.ppjun.android.smzdm.di.module

import com.jess.arms.di.scope.ActivityScope
import com.ppjun.android.smzdm.mvp.contract.ArticleInfoContract
import com.ppjun.android.smzdm.mvp.contract.PriceInfoContract
import com.ppjun.android.smzdm.mvp.model.ArticleInfoModel
import com.ppjun.android.smzdm.mvp.model.PriceInfoModel
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides


@Module
class ArticleInfoModule constructor(var view: ArticleInfoContract.View) {

    @ActivityScope
    @Provides
    fun provideArticleInfoView(): ArticleInfoContract.View = view


    @ActivityScope
    @Provides
    fun provideArticleInfoModel(model: ArticleInfoModel): ArticleInfoContract.Model = model


    @ActivityScope
    @Provides
    fun provideRxPermissions(): RxPermissions = RxPermissions(view.getTheActivity())


}