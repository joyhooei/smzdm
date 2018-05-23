package com.ppjun.android.smzdm.mvp.ui.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleData
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import com.ppjun.android.smzdm.mvp.ui.holder.ArticleHolder
import com.ppjun.android.smzdm.mvp.ui.holder.ArticleListHolder
import com.ppjun.android.smzdm.mvp.ui.holder.PriceListHolder

class ArticleListAdapter(var list: ArrayList<ArticleData>) : DefaultAdapter<ArticleData>(list) {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_article_list
    }

    override fun getHolder(v: View?, viewType: Int): BaseHolder<ArticleData> {
        return ArticleListHolder(requireNotNull(v))
    }
}