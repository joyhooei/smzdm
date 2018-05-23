package com.ppjun.android.smzdm.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.ppjun.android.smzdm.di.module.ArticleInfoModule
import com.ppjun.android.smzdm.mvp.ui.activity.ArticleInfoActivity
import dagger.Component


@ActivityScope
@Component(modules = [(ArticleInfoModule::class)], dependencies = [(AppComponent::class)])
interface ArticleInfoComponent {
    fun inject(actiovity: ArticleInfoActivity)
}