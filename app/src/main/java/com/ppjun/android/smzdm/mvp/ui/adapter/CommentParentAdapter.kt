package com.ppjun.android.smzdm.mvp.ui.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.mvp.model.entity.main.InfoComment
import com.ppjun.android.smzdm.mvp.model.entity.main.CommentParent
import com.ppjun.android.smzdm.mvp.ui.holder.CommentHolder
import com.ppjun.android.smzdm.mvp.ui.holder.CommentParentHolder

class CommentParentAdapter(var list: ArrayList<CommentParent>) : DefaultAdapter<CommentParent>(list) {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_info_comment_parent
    }

    override fun getHolder(v: View, viewType: Int): BaseHolder<CommentParent> {
        return CommentParentHolder(v)
    }
}