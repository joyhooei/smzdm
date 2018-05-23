package com.ppjun.android.smzdm.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.ppjun.android.smzdm.di.module.PriceInfoModule
import com.ppjun.android.smzdm.mvp.ui.activity.PriceInfoActivity
import dagger.Component


@ActivityScope
@Component(modules = [(PriceInfoModule::class)], dependencies = [(AppComponent::class)])
interface PriceInfoComponent {
    fun inject(ativity: PriceInfoActivity)
}