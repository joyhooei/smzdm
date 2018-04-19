package com.ppjun.android.smzdm.mvp.ui.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.holder.MainHolder

class MainAdapter( list:ArrayList<Row>) :DefaultAdapter<Row>(list) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_main_list

    override fun getHolder(v: View?, viewType: Int): BaseHolder<Row> =MainHolder(v!!)
}