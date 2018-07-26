package com.ppjun.android.smzdm.mvp.ui.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import kotlinx.android.synthetic.main.splash_ui.*
import okhttp3.*
import java.io.IOException

class SplashActivity : BaseUI<IPresenter>() {


    override fun initData(savedInstanceState: Bundle?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()


    }

    override fun setupActivityComponent(appComponent: AppComponent?) {


    }

    override fun initView(savedInstanceState: Bundle?): Int {
        intent.putExtra("isInitToolbar", false)
        return R.layout.splash_ui

    }
}