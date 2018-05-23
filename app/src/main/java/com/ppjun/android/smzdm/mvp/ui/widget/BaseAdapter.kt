package com.ppjun.android.smzdm.mvp.ui.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.zhy.autolayout.utils.AutoUtils

abstract class BaseAdapter(context: Context, layoutHelper: LayoutHelper, count: Int): DelegateAdapter.Adapter<BaseAdapter.MainViewHolder>() {


    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder


    abstract  override fun onBindViewHolder(holder: MainViewHolder, position: Int)



    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            AutoUtils.autoSize(itemView)
            createdTimes++
            existing++
        }


        @Throws(Throwable::class)
        protected  fun finalize() {
            existing--

        }

        companion object {

            @Volatile
            var existing = 0
            var createdTimes = 0
        }
    }
}