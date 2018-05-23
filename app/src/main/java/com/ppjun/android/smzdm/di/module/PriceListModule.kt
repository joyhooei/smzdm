package com.ppjun.android.smzdm.di.module

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.mvp.contract.MainContract
import com.ppjun.android.smzdm.mvp.contract.PriceContract
import com.ppjun.android.smzdm.mvp.model.MainModel
import com.ppjun.android.smzdm.mvp.model.PriceListModel
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.adapter.MainAdapter
import com.ppjun.android.smzdm.mvp.ui.adapter.PriceListAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides


@Module
class PriceListModule constructor(var view: PriceContract.View) {


    @FragmentScope
    @Provides
    fun providePriceListView(): PriceContract.View = view

    @FragmentScope
    @Provides
    fun providePriceListModel(model: PriceListModel): PriceContract.Model = model


    @FragmentScope
        @Provides
        fun provideRxPermissions(): RxPermissions = RxPermissions(view.getTheActivity())


        @FragmentScope
        @Provides
        fun provideLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(view.getTheActivity()
                , LinearLayoutManager.VERTICAL, false)


        @FragmentScope
        @Provides
        fun provideRowList(): ArrayList<PriceRow> = ArrayList()

        @FragmentScope
        @Provides
        fun providePriceListAdapter(list: ArrayList<PriceRow>): DefaultAdapter<PriceRow> = PriceListAdapter(list)

}