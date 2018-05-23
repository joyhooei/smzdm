package com.ppjun.android.smzdm.di.module

import com.jess.arms.di.scope.ActivityScope
import com.ppjun.android.smzdm.mvp.contract.NewsInfoContract
import com.ppjun.android.smzdm.mvp.contract.PriceInfoContract
import com.ppjun.android.smzdm.mvp.model.NewsInfoModel
import com.ppjun.android.smzdm.mvp.model.PriceInfoModel
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides


@Module
class NewsInfoModule(var view: NewsInfoContract.View) {


    @ActivityScope
    @Provides
    fun provideNewsInfoView(): NewsInfoContract.View = view


    @ActivityScope
    @Provides
    fun provideNewsInfoModel(model: NewsInfoModel): NewsInfoContract.Model = model


    @ActivityScope
    @Provides
    fun provideRxPermissions(): RxPermissions = RxPermissions(view.getTheActivity())


}