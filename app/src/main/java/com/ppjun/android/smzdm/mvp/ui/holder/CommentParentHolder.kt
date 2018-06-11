package com.ppjun.android.smzdm.mvp.ui.holder

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jess.arms.base.BaseHolder
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.mvp.model.entity.main.CommentParent
import com.ppjun.android.smzdm.mvp.model.entity.main.InfoComment
import kotlinx.android.synthetic.main.item_info_comment.view.*

class CommentParentHolder (itemView: android.view.View) : BaseHolder<CommentParent>(itemView) {
    private var mAppComponent: AppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
    private var mImageLoader: ImageLoader = mAppComponent.imageLoader()
    override fun setData(data: CommentParent?, position: Int) {
        val commentUserImg = itemView.commentUserImg
        val commentUserName = itemView.commentUserName
        val commentUserContent = itemView.commentUserContent
        val commentInfoFloor = itemView.commentInfoFloor
        val commentInfoTime = itemView.commentInfoTime
        val commentParentRV=itemView.commentParentRV
        commentUserName.text = data?.commentAuthor

        commentInfoTime.text = data?.formatDateClient

        commentUserContent.text = Html.fromHtml(data?.commentContent, object : Html.ImageGetter {
            override fun getDrawable(source: String?): Drawable {
                val urlDrawable = URLDrawable(null)
                GlideArms.with(itemView.context)
                        .asDrawable()
                        .load(source)
                        .into(object : SimpleTarget<Drawable>() {
                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                urlDrawable.drawable = resource
                            }
                        })

                return urlDrawable

            }

        }, null)

        GlideArms.with(itemView.context)
                .load(data?.head)
                .circleCrop()
                .into(commentUserImg)




    }


    data class URLDrawable(var drawable: Drawable?) : BitmapDrawable(){}}