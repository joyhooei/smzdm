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
import java.math.BigDecimal
import java.text.DecimalFormat

class MainHolder(itemView: View) : BaseHolder<Row>(itemView) {
    private var mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
    private var mImageLoader: ImageLoader = mAppComponent.imageLoader()


    override fun setData(data: Row?, position: Int) {
        val mainProductTitle = itemView.mainProductTitle
        val mainProductImg = itemView.mainProductImage
        val mainProductPrice = itemView.mainProductPrice
        val mainProductShop = itemView.mainProductShop
        val mainProductTime = itemView.mainProductTime
        val mainProductComment = itemView.mainProductComment
        val mainProductWorth = itemView.mainProductWorth
        val mainThumbImg = itemView.mainThumbImg
        val mainProductTip=itemView.mainProductTip
        val mainProductWorthTv = itemView.mainProductWorthTv
        mainProductTip.text= data?.articleChannelName
        mainProductTitle.text = data?.articleTitle
        mainProductPrice.text = data?.articlePrice

        if (!data?.articleRzlxName.isNullOrEmpty()) {
            mainProductShop.text = data?.articleRzlxName
        } else {
            mainProductShop.text = data?.articleMall
        }

        if (data?.articleChannelId == "6") {
            mainThumbImg.visibility = View.VISIBLE
            mainProductWorthTv.visibility = View.GONE
            mainProductWorth.text = data.articleLoveCount

        } else {
            mainThumbImg.visibility = View.GONE
            mainProductWorthTv.visibility = View.VISIBLE
            if ((data!!.articleWorthy.toInt().plus(data.articleUnworthy.toInt())) == 0) {
                mainProductWorth.text = "0%"
            } else {
                mainProductWorth.text = "${DecimalFormat("0").format(data.articleWorthy.toDouble().div(data.articleUnworthy.toDouble() + data.articleWorthy.toDouble()) * 100)}%"
            }
        }

        mainProductTime.text = data.articleFormatDate
        mainProductComment.text = data.articleComment


        mImageLoader.loadImage(itemView.context, ImageConfigImpl.builder()
                .url(data.articlePic)
                .imageView(mainProductImg)
                .build())
    }

    override fun onRelease() {
        super.onRelease()

    }
}