package com.ppjun.android.smzdm.mvp.ui.holder

import android.content.Intent
import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceRow
import com.ppjun.android.smzdm.mvp.ui.activity.PriceInfoActivity


import kotlinx.android.synthetic.main.item_price_list.view.*
import java.text.DecimalFormat

class PriceListHolder( itemView:View):BaseHolder<PriceRow>(itemView) {
    private var mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
    private var mImageLoader: ImageLoader = mAppComponent.imageLoader()


    override fun setData(data: PriceRow?, position: Int) {
        val mainProductTitle = itemView.priceListTitle
        val mainProductImg = itemView.priceListImage
        val mainProductPrice = itemView.priceListPrice
        val mainProductShop = itemView.priceListShop
        val mainProductTime = itemView.priceListTime
        val mainProductComment = itemView.priceListComment
        val mainProductWorth = itemView.priceListWorth
        val mainProductTip=itemView.priceListTip
        val mainProductWorthTv = itemView.priceListWorthTv
        mainProductTip.text= data?.articleTypeName
        mainProductTitle.text = data?.articleTitle
        mainProductPrice.text = data?.articlePrice


            mainProductShop.text = if(requireNotNull(data).articleMall.isEmpty())"什么值得买" else requireNotNull(data).articleMall
            mainProductWorthTv.visibility = View.VISIBLE
            if ((data!!.articleWorthy.toInt().plus(data.articleUnworthy.toInt())) == 0) {
                mainProductWorth.text = "0%"
            } else {
                mainProductWorth.text = "${DecimalFormat("0").format(data.articleWorthy.toDouble().div(data.articleUnworthy.toDouble() + data.articleWorthy.toDouble()) * 100)}%"

        }

        mainProductTime.text = data.articleFormatDate
        mainProductComment.text = data.articleComment


        mImageLoader.loadImage(itemView.context, ImageConfigImpl.builder()
                .url(data.articlePic)
                .imageView(mainProductImg)
                .build())

        itemView.setOnClickListener {
            val resultIntent= Intent(itemView.context, PriceInfoActivity::class.java)
            resultIntent.putExtra(Constant.ID,data.articleId)
            itemView.context.startActivity(resultIntent)
        }
}}