package com.ppjun.android.smzdm.mvp.ui.viewbinder

import android.content.Intent
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.activity.ArticleInfoActivity
import com.ppjun.android.smzdm.mvp.ui.activity.NewsInfoActivity
import com.ppjun.android.smzdm.mvp.ui.activity.PriceInfoActivity
import kotlinx.android.synthetic.main.item_article_list.view.*
import kotlinx.android.synthetic.main.item_main_list.view.*
import java.text.DecimalFormat

class MainDelegateAdapter(var datas: List<Row>) : DelegateAdapter.Adapter<RecyclerView.ViewHolder>() {

    private val ARTICLE = 0
    private val PRICE = 1
    private val NEWS = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  when (viewType) {
            ARTICLE -> {
                 ArticleHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_article_list, parent, false))

            }
            else -> {
                 MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false))
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (datas[position].articleFirstChannelName == "好文") {
            ARTICLE
        } else if (datas[position].articleChannelName == "优惠" || datas[position].articleChannelName == "海淘") {
            PRICE
        } else {
            NEWS
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }


    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }





    override fun onBindViewHolder(aholder: RecyclerView.ViewHolder, position: Int) {
        val data = datas[position]
        val mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(aholder.itemView.context)
        val mImageLoader: ImageLoader = mAppComponent.imageLoader()



        if (getItemViewType(position)==ARTICLE) {


           val  holder =aholder as ArticleHolder

            holder.mainAuthor.text = data?.articleReferrals
            holder.mainArticleTime.text = data?.articleFormatDate
            holder.mainArticleTitle.text = data?.articleTitle
            holder.mainArticleTag.text = data?.tagCategory
            holder.mainArticleCollect.text = data?.articleCollection
            holder.mainArticleComment.text = data?.articleComment
            holder.mainArticleTip.text = data?.articleChannelName
            mImageLoader.loadImage(holder.itemView.context, ImageConfigImpl.builder()
                    .imageView(holder.mainArticleImg).url(data?.articlePic).build())

            Glide.with(holder.itemView.context).applyDefaultRequestOptions(RequestOptions().circleCrop())
                    .load(data?.articleAvator)
                    .into(holder.mainAuthorImg)
            holder.itemView.setOnClickListener {
                val resultIntent = Intent(holder.itemView.context, ArticleInfoActivity::class.java)
                resultIntent.putExtra(Constant.ID, data?.articleId)
                resultIntent.putExtra(Constant.COLLECT, data?.articleCollection)
                resultIntent.putExtra(Constant.COMMENT, data?.articleComment)
                holder. itemView.context.startActivity(resultIntent)

            }

        } else {

            val  holder =aholder as MainHolder

            holder.mainProductTip.text = data?.articleChannelName
            holder.mainProductTitle.text = data?.articleTitle
            holder.mainProductPrice.text = data?.articlePrice

            if (!data?.articleRzlxName.isNullOrEmpty()) {
                holder.mainProductShop.text = data?.articleRzlxName
            } else {
                holder.mainProductShop.text = data?.articleMall
            }

            if (data?.articleChannelId == "6") {
                holder.mainThumbImg.visibility = View.VISIBLE
                holder.mainProductWorthTv.visibility = View.GONE
                holder.mainProductWorth.text = data.articleLoveCount
                holder.itemView.setOnClickListener {
                    val resultIntent = Intent(holder.itemView.context, NewsInfoActivity::class.java)
                    resultIntent.putExtra(Constant.ID, data.articleId)
                    resultIntent.putExtra(Constant.COLLECT, data.articleLoveCount)
                    resultIntent.putExtra(Constant.COMMENT, data.articleComment)
                    holder.itemView.context.startActivity(resultIntent)
                }

            } else {
                holder.mainThumbImg.visibility = View.GONE
                holder.mainProductWorthTv.visibility = View.VISIBLE

                if ((requireNotNull(data).articleWorthy.toInt().plus(requireNotNull(data).articleUnworthy.toInt())) == 0) {
                    holder.mainProductWorth.text = "0%"
                } else {
                    holder.mainProductWorth.text = "${DecimalFormat("0").format(requireNotNull(data).articleWorthy.toDouble()
                            .div(requireNotNull(data).articleUnworthy.toDouble() + requireNotNull(data).articleWorthy.toDouble()) * 100)}%"
                }

                holder.itemView.setOnClickListener {
                    val resultIntent = Intent(holder.itemView.context, PriceInfoActivity::class.java)
                    resultIntent.putExtra(Constant.ID, requireNotNull(data).articleId)
                    holder.itemView.context.startActivity(resultIntent)
                }
            }

            holder.mainProductTime.text = requireNotNull(data).articleFormatDate
            holder.mainProductComment.text = requireNotNull(data).articleComment


            mImageLoader.loadImage(holder.itemView.context, ImageConfigImpl.builder()
                    .url(requireNotNull(data).articlePic)
                    .imageView(holder.mainProductImg)
                    .build())

        }
    }


    inner class MainHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val mainProductTitle = itemView.mainProductTitle
        val mainProductImg = itemView.mainProductImage
        val mainProductPrice = itemView.mainProductPrice
        val mainProductShop = itemView.mainProductShop
        val mainProductTime = itemView.mainProductTime
        val mainProductComment = itemView.mainProductComment
        val mainProductWorth = itemView.mainProductWorth
        val mainThumbImg = itemView.mainThumbImg
        val mainProductTip = itemView.mainProductTip
        val mainProductWorthTv = itemView.mainProductWorthTv
    }


    inner class ArticleHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val mainArticleImg = itemView.mainArticleImg
        val mainAuthorImg = itemView.mainAuthorImg
        val mainAuthor = itemView.mainAuthor
        val mainArticleTime = itemView.mainArticleTime
        val mainArticleTitle = itemView.mainArticleTitle
        val mainArticleTag = itemView.mainArticleTag
        val mainArticleComment = itemView.mainArticleComment
        val mainArticleCollect = itemView.mainArticleCollect
        val mainArticleTip = itemView.mainArticleTip

    }
}