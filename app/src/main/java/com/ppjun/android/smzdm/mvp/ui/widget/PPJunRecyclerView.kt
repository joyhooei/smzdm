package com.ppjun.android.smzdm.mvp.ui.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ppjun.android.smzdm.R
import android.widget.RelativeLayout
import com.jess.arms.utils.LogUtils


class PPJunRecyclerView constructor(context: Context, attr: AttributeSet) : RecyclerView(context, attr) {


    fun checkIfEmpty() {
            val emptyViewVisible = adapter.itemCount == 0
            theEmptyView?.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            visibility = if (emptyViewVisible) View.GONE else View.VISIBLE

    }

    var theEmptyView: View? = null
    private var observer = object : AdapterDataObserver() {

        override fun onChanged() {
            checkIfEmpty()

        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            onChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            onChanged()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            onChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            onChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            onChanged()
        }
    }

    fun setEmptyView(view: View) {
        this.theEmptyView = view

        allEmptyView()
    }

    fun setDefaultEmptyView() {
        val view = LayoutInflater.from(this.context).inflate(R.layout.empty_view_ui, null)
        this.theEmptyView = view

        allEmptyView()
    }

    override fun setAdapter(newAdapter: Adapter<*>) {
        val oldAdapter = this.adapter
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(newAdapter)
        newAdapter.registerAdapterDataObserver(observer)

    }

    fun allEmptyView() {
        if (theEmptyView?.parent == null) {
            //如果emptyView没有父布局，则添加至当前RecyclerView的父View中
            val parent = parent
            if (parent is RelativeLayout) {
                //相对布局
                val layoutParams = RelativeLayout.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT)
                val parentView = getParent() as ViewGroup
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
                parentView.addView(theEmptyView, layoutParams)
            } else if (getParent() is ViewGroup) {
                val parentView = getParent() as ViewGroup
                parentView.addView(theEmptyView)
            }
        }
    }

}