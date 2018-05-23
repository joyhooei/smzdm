package com.ppjun.android.smzdm.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.di.module.ArticleListModule
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.ArticleFragment
import dagger.Component


@FragmentScope
@Component(modules = [(ArticleListModule::class)], dependencies = [(AppComponent::class)])
interface ArticleListComponent {
    fun inject(fragment: ArticleFragment)
}