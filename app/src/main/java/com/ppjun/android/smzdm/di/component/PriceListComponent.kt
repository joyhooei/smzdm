package com.ppjun.android.smzdm.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.di.module.PriceListModule
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.PriceFragment
import dagger.Component


@FragmentScope
@Component(modules = [(PriceListModule::class)],dependencies = [(AppComponent::class)])
interface PriceListComponent {
    fun inject(fragment: PriceFragment)
}