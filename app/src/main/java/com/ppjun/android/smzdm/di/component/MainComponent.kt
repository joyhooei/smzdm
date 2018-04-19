package com.ppjun.android.smzdm.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.di.module.MainModule
import com.ppjun.android.smzdm.mvp.model.MainModel
import com.ppjun.android.smzdm.mvp.ui.activity.MainActivity
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.MainFragment
import dagger.Component
import javax.inject.Singleton


@FragmentScope
@Component(modules = [(MainModule::class)],dependencies = [(AppComponent::class)])
interface MainComponent {
    fun inject(fragment: MainFragment)
}