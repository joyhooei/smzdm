package com.ppjun.android.smzdm.di.module

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.FragmentScope
import com.ppjun.android.smzdm.mvp.contract.PriceContract
import com.ppjun.android.smzdm.mvp.contract.PriceSearchContract
import com.ppjun.android.smzdm.mvp.model.PriceListModel
import com.ppjun.android.smzdm.mvp.model.PriceSearchModel
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import com.ppjun.android.smzdm.mvp.ui.adapter.PriceListAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides

@Module
class PriceSearchModule constructor(var view: PriceSearchContract.View)  {


    @FragmentScope
    @Provides
    fun providePriceSearchView(): PriceSearchContract.View = view

    @FragmentScope
    @Provides
    fun providePriceSearchModel(model: PriceSearchModel): PriceSearchContract.Model = model


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