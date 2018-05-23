package com.ppjun.android.smzdm.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.di.module.ArticleSearchModule
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.ArticleSearchFragment
import dagger.Component


@FragmentScope
@Component(modules = [(ArticleSearchModule::class)],dependencies = [(AppComponent::class)])
interface ArticleSearchComponent {
    fun inject(fragment:ArticleSearchFragment)
}