package com.ppjun.android.smzdm.mvp.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paginate.recycler.LoadingListItemCreator
import com.ppjun.android.smzdm.R

class BottomLoadingCreator :LoadingListItemCreator {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        val view=LayoutInflater.from(parent?.context)
                .inflate(R.layout.loading_view,parent,false)
        return LoadingHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }



    class LoadingHolder( view:View):RecyclerView.ViewHolder(view){
    }
}