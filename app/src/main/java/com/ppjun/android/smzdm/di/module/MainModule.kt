package com.ppjun.android.smzdm.di.module

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.mvp.contract.MainContract
import com.ppjun.android.smzdm.mvp.model.MainModel
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.adapter.MainAdapter
import com.ppjun.android.smzdm.mvp.ui.holder.MainHolder
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides


@Module
class MainModule constructor(var view: MainContract.View) {


    @FragmentScope
    @Provides
    fun provideMainView(): MainContract.View= view


    @FragmentScope
    @Provides
    fun provideMainModel(model: MainModel):MainContract.Model = model

    @FragmentScope
    @Provides
    fun provideRxPermissions():RxPermissions = RxPermissions(view.getTheActivity())


    @FragmentScope
    @Provides
    fun provideLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(view.getTheActivity()
            , LinearLayoutManager.VERTICAL, false)

    @FragmentScope
    @Provides
    fun provideRowList() :ArrayList<Row> = ArrayList<Row>()


    @FragmentScope
    @Provides
    fun provideRowAdapter(list: ArrayList<Row>): DefaultAdapter<Row> = MainAdapter(list)
}