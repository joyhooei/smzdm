package com.ppjun.android.smzdm.di.component

import android.support.v7.app.AppCompatDelegate
import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.ppjun.android.smzdm.di.module.PriceCommentModule
import com.ppjun.android.smzdm.mvp.ui.activity.PriceCommentActivity
import dagger.Component


@ActivityScope
@Component(modules = [(PriceCommentModule::class)], dependencies = [(AppComponent::class)])
interface PriceCommentComponent {
    fun inject(activity: PriceCommentActivity)

}