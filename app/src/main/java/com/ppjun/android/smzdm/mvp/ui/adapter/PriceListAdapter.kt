package com.ppjun.android.smzdm.mvp.ui.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import com.ppjun.android.smzdm.mvp.ui.holder.PriceListHolder
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class PriceListAdapter(var list: ArrayList<PriceRow>) : DefaultAdapter<PriceRow>(list) {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_price_list
    }

    override fun getHolder(v: View?, viewType: Int): BaseHolder<PriceRow> {


        return PriceListHolder(v!!)
    }
}