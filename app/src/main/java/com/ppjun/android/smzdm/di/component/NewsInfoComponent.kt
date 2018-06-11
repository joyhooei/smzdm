package com.ppjun.android.smzdm.di.component

import android.support.v4.app.AppOpsManagerCompat
import android.support.v7.app.AppCompatDelegate
import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.ppjun.android.smzdm.di.module.NewsInfoModule
import com.ppjun.android.smzdm.mvp.ui.activity.NewsInfoActivity
import dagger.Component
import dagger.Module


@ActivityScope
@Component(modules = [(NewsInfoModule::class)], dependencies = [(AppComponent::class)])
interface NewsInfoComponent {
    fun inject(activity: NewsInfoActivity)

}