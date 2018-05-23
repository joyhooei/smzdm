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
        splashLottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {


            }

            override fun onAnimationEnd(animation: Animator?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }


        })
        splashLottieView.addAnimatorUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {

            }

        })

    }

    override fun setupActivityComponent(appComponent: AppComponent?) {


    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.splash_ui

    }
}