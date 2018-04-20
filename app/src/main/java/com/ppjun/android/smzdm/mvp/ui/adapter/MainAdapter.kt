package com.ppjun.android.smzdm.mvp.ui.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.holder.ArticleHolder
import com.ppjun.android.smzdm.mvp.ui.holder.MainHolder

class MainAdapter(var list: ArrayList<Row>) : DefaultAdapter<Row>(list) {

    private val ARTICLE = 0
    private val PRICE = 1
    private val NEWS = 2

    override fun getItemViewType(position: Int): Int {
        /**
         * id=11 好文
         * id=1 优惠 id=5 海淘
         * id=6  资讯
         */

        return if (list[position].articleirstChannelName == "好文") {
            ARTICLE
        } else if (list[position].articleChannelName == "优惠" || list[position].articleChannelName == "海淘") {
            PRICE
        } else {
            NEWS
        }

    }

    override fun getLayoutId(viewType: Int): Int {
        return when (viewType) {
            ARTICLE -> R.layout.item_article_list
            PRICE -> R.layout.item_main_list
            else -> R.layout.item_main_list

        }
    }

    override fun getHolder(v: View?, viewType: Int): BaseHolder<Row> {
        return when (viewType) {
            ARTICLE -> ArticleHolder(v!!)
            PRICE -> MainHolder(v!!)
            else -> MainHolder(v!!)
        }

    }
}