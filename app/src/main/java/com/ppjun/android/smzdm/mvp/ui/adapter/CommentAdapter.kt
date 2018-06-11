package com.ppjun.android.smzdm.mvp.ui.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.mvp.model.entity.main.InfoComment
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.holder.CommentHolder

class CommentAdapter(var list: ArrayList<InfoComment>) : DefaultAdapter<InfoComment>(list)  {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_info_comment
    }

    override fun getHolder(v: View, viewType: Int): BaseHolder<InfoComment> {
        return CommentHolder(v)
    }
}