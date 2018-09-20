package com.ppjun.android.smzdm.mvp.ui.holder

import android.app.Activity
import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.app.utils.PreferencesUtil
import com.ppjun.android.smzdm.mvp.model.entity.main.ColorTheme
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import kotlinx.android.synthetic.main.item_color.view.*

class ThemeHolder(itemView: View) : BaseHolder<ColorTheme>(itemView) {


    override fun setData(data: ColorTheme?, position: Int) {

        val selectColorImg = itemView.selectColorImg
        selectColorImg.setImageResource(R.color.colorAccent)


        //点击图标设置颜色
        itemView.setOnClickListener {
            when (position) {
                0 -> {
                    PreferencesUtil.putString(itemView.context, Constant.THEME_COLOR, "mint")
                    (itemView.context as Activity).recreate()
                }

                1 -> {
                    PreferencesUtil.putString(itemView.context, Constant.THEME_COLOR, "mint1")
                    (itemView.context as Activity).recreate()
                }
                else -> {
                    PreferencesUtil.putString(itemView.context, Constant.THEME_COLOR, "mint")
                    (itemView.context as Activity).recreate()
                }
            }

        }

    }
}