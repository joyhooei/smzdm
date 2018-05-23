package com.ppjun.android.smzdm.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.di.module.PriceSearchModule
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.PriceSearchFragment
import dagger.Component


@FragmentScope
@Component(modules = [(PriceSearchModule::class)],dependencies = [(AppComponent::class)])
interface PriceSearchComponent {
    fun inject(fragment:PriceSearchFragment)
}