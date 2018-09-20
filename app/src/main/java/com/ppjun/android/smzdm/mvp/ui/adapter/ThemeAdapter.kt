package com.ppjun.android.smzdm.mvp.ui.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.mvp.model.entity.main.ColorTheme
import com.ppjun.android.smzdm.mvp.ui.holder.ThemeHolder

class ThemeAdapter(var list: ArrayList<ColorTheme>) : DefaultAdapter<ColorTheme>(list) {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_color
    }

    override fun getHolder(v: View, viewType: Int): BaseHolder<ColorTheme> {
        return ThemeHolder(v)
    }
}