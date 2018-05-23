package com.ppjun.android.smzdm.mvp.ui.holder

import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jess.arms.base.BaseHolder
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.activity.ArticleInfoActivity
import com.ppjun.android.smzdm.mvp.ui.activity.PriceInfoActivity
import kotlinx.android.synthetic.main.article_info_ui.*
import kotlinx.android.synthetic.main.item_article_list.view.*

class ArticleHolder(itemView: android.view.View) : BaseHolder<Row>(itemView) {
    private var mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
    private var mImageLoader: ImageLoader = mAppComponent.imageLoader()
    override fun setData(data: Row?, position: Int) {

        val mainArticleImg = itemView.mainArticleImg
        val mainAuthorImg = itemView.mainAuthorImg
        val mainAuthor = itemView.mainAuthor
        val mainArticleTime = itemView.mainArticleTime
        val mainArticleTitle = itemView.mainArticleTitle
        val mainArticleTag = itemView.mainArticleTag
        val mainArticleComment = itemView.mainArticleComment
        val mainArticleCollect = itemView.mainArticleCollect
        val mainArticleTip = itemView.mainArticleTip
        mainAuthor.text = data?.articleReferrals
        mainArticleTime.text = data?.articleFormatDate
        mainArticleTitle.text = data?.articleTitle
        mainArticleTag.text = data?.tagCategory
        mainArticleCollect.text = data?.articleCollection
        mainArticleComment.text = data?.articleComment
        mainArticleTip.text = data?.articleChannelName
        mImageLoader.loadImage(itemView.context, ImageConfigImpl.builder()
                .imageView(mainArticleImg).url(data?.articlePic).build())

        Glide.with(itemView.context).applyDefaultRequestOptions( RequestOptions().circleCrop())
                .load(data?.articleAvator)
                .into(mainAuthorImg)
        itemView.setOnClickListener {
            val resultIntent= Intent(itemView.context, ArticleInfoActivity::class.java)
            resultIntent.putExtra(Constant.ID,data?.articleId)
            resultIntent.putExtra(Constant.COLLECT,data?.articleCollection)
            resultIntent.putExtra(Constant.COMMENT,data?.articleComment)
            itemView.context.startActivity(resultIntent)

        }

    }


}