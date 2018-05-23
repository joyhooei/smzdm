package com.ppjun.android.smzdm.mvp.ui.holder

import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jess.arms.base.BaseHolder
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleData
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.ui.activity.ArticleInfoActivity
import kotlinx.android.synthetic.main.item_article_list.view.*

class ArticleListHolder(itemView: android.view.View) : BaseHolder<ArticleData>(itemView) {
    private var mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
    private var mImageLoader: ImageLoader = mAppComponent.imageLoader()
    override fun setData(data: ArticleData?, position: Int) {

        val mainArticleImg = itemView.mainArticleImg
        val mainAuthorImg = itemView.mainAuthorImg
        val mainAuthor = itemView.mainAuthor
        val mainArticleTime = itemView.mainArticleTime
        val mainArticleTitle = itemView.mainArticleTitle
        val mainArticleTag = itemView.mainArticleTag
        val mainArticleComment = itemView.mainArticleComment
        val mainArticleCollect = itemView.mainArticleCollect
        val mainArticleTip = itemView.mainArticleTip
        val mainArticleLine=itemView.mainArticleLine

        mainAuthor.text = data?.articleReferrals
        mainArticleTime.text = data?.articleFormatDate
        mainArticleTitle.text = data?.articleTitle
        mainArticleTag.text = data?.tagCategory
        mainArticleCollect.apply {
           text=data?.articleCollection
        }




        mainArticleComment.text = data?.articleComment
        mainArticleTip.visibility= View.GONE
        mainArticleLine.visibility= View.GONE
        mImageLoader.loadImage(itemView.context, ImageConfigImpl.builder()
                .imageView(mainArticleImg).url(data?.articlePic).build())


        GlideArms.with(itemView.context)
                .load(data?.articleAvatar)
                .circleCrop()
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