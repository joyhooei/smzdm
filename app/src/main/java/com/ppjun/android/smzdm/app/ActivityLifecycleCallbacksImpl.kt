package com.ppjun.android.smzdm.app

import android.app.Activity
import android.app.Application
import android.app.ApplicationErrorReport
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toolbar
import com.jess.arms.mvp.IPresenter
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.ui.activity.SearchResultActivity
import timber.log.Timber

class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {
        Timber.w(activity.toString() + "-onActivityPaused")
    }

    override fun onActivityResumed(activity: Activity?) {
        Timber.w(activity.toString() + "-onActivityResumed")
    }

    override fun onActivityStarted(activity: Activity?) {
        Timber.w(activity.toString() + "-onActivityStarted")
        if(activity is BaseUI<*>) {
            if (activity.intent.getBooleanExtra(Constant.IS_INIT_TOOLBAR, false).not()) {
                activity.intent.putExtra(Constant.IS_INIT_TOOLBAR, true)
                if (activity.findViewById<Toolbar>(R.id.toolbar) != null) {
                    if (activity is AppCompatActivity) {
                        activity.setSupportActionBar(activity.findViewById(R.id.toolbar))
                        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.setActionBar(activity.findViewById(R.id.toolbar) as Toolbar)
                        activity.actionBar.setDisplayShowTitleEnabled(false)
                    }
                }
            }

            activity.findViewById<TextView>(R.id.toolbarTitle)?.setText(R.string.title)
            activity.findViewById<RelativeLayout>(R.id.toolBack)?.setOnClickListener {
                activity.onBackPressed()
            }
            activity.findViewById<ImageView>(R.id.toolSearch)?.setOnClickListener {
                activity.startActivity(Intent(activity,SearchResultActivity::class.java))

            }
        }
    }



override fun onActivityDestroyed(activity: Activity?) {
    Timber.w(activity.toString() + "-onActivityDestroyed")
}

override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    Timber.w(activity.toString() + "-onActivitySaveInstanceState")
}

override fun onActivityStopped(activity: Activity?) {
    Timber.w(activity.toString() + "-onActivityStopped")
}

override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    Timber.w(activity.toString() + "-onActivityCreated")
}
}