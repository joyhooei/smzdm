package com.ppjun.android.smzdm.mvp.ui.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import kotlinx.android.synthetic.main.item_main_list.view.*

class MainHolder(itemView: View) : BaseHolder<Row>(itemView) {
    private var mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
    private var mImageLoader: ImageLoader = mAppComponent.imageLoader()


    override fun setData(data: Row?, position: Int) {
        var mainProductTitle = itemView.mainProductTitle
        val mainProductImg=itemView.mainProductImage
        val mainProductPrice=itemView.mainProductPrice
        mainProductTitle.text = data?.articleTitle
        mainProductPrice.text=data?.articlePrice
        mImageLoader.loadImage(itemView.context,ImageConfigImpl.builder()
                .url(data?.articlePic)
                .imageView(mainProductImg)
                .build())


    }

    override fun onRelease() {
        super.onRelease()

    }
}