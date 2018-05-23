package com.ppjun.android.smzdm.mvp.ui.widget

import android.support.v4.util.SparseArrayCompat
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter

class HeaderAndFooterWrapper<T>(var list: ArrayList<T>, var adapter: DefaultAdapter<T>) : DefaultAdapter<T>(list) {
    override fun getHolder(v: View?, viewType: Int): BaseHolder<T> {
        return adapter.getHolder(v,viewType)
    }

    val BASE_HEADER_ITEM = 10000
    val BASE_FOOTER_ITEM = 20000
    val mHeaderViews = SparseArrayCompat<View>()
    val mFooterViews = SparseArrayCompat<View>()


    override fun getLayoutId(viewType: Int): Int {
        return adapter.getLayoutId(viewType)
    }



    fun getRealItemCount() = adapter.itemCount

    fun isHeaderViewPos(pos: Int) = pos < getHeadersCount()

    fun isFooterViewPos(pos: Int) = pos >= getHeadersCount() + getRealItemCount()

    fun addHeaderView(view: View) {
        mHeaderViews.put(BASE_HEADER_ITEM + mHeaderViews.size(), view)
    }

    fun addFooterView(view: View) {
        mFooterViews.put(BASE_FOOTER_ITEM + mHeaderViews.size(), view)
    }

    fun getHeadersCount() = mHeaderViews.size()

    fun getFootersCount() = mFooterViews.size()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {

        if (mHeaderViews.get(viewType) != null) {

            return adapter.getHolder(mHeaderViews[viewType], viewType)

        } else if (mFooterViews.get(viewType) != null) {
            return adapter.getHolder(mFooterViews[viewType], viewType)
        }
        return adapter.onCreateViewHolder(parent, viewType)

    }


    override fun getItemViewType(position: Int): Int {
        if(isHeaderViewPos(position)){
            return mHeaderViews.keyAt(position)
        }else if(isFooterViewPos(position)){
            return mFooterViews.keyAt(position-getHeadersCount()-getRealItemCount())
        }
        return adapter.getItemViewType(position-getHeadersCount())
    }

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {

        if(isHeaderViewPos(position)){
            return
        }
        if(isFooterViewPos(position)){
            return
        }
        adapter.onBindViewHolder(holder, position-getHeadersCount())
    }

    override fun getItemCount(): Int {
        return getHeadersCount()+getFootersCount()+getRealItemCount()
    }
}