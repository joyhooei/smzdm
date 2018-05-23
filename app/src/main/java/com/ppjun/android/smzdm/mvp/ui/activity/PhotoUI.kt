package com.ppjun.android.smzdm.mvp.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant
import kotlinx.android.synthetic.main.photo_ui.*

class PhotoUI :BaseUI<IPresenter>(){
    override fun initData(savedInstanceState: Bundle?) {

        val imageUrl = intent.getStringExtra(Constant.KEY)
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        basePhotoView.setImageBitmap(resource)
                    }
                })

    }

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.photo_ui
}