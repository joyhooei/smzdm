package com.ppjun.android.smzdm.di.module

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.mvp.contract.ArticleListContract
import com.ppjun.android.smzdm.mvp.contract.PriceInfoContract
import com.ppjun.android.smzdm.mvp.model.ArticleListModel
import com.ppjun.android.smzdm.mvp.model.PriceInfoModel
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides


@Module
class PriceInfoModule constructor(var view: PriceInfoContract.View) {


    @ActivityScope
    @Provides
    fun providePriceInfoView(): PriceInfoContract.View = view


    @ActivityScope
    @Provides
    fun providePriceInfoModel(model: PriceInfoModel): PriceInfoContract.Model = model


    @ActivityScope
    @Provides
    fun provideRxPermissions(): RxPermissions = RxPermissions(view.getTheActivity())

}