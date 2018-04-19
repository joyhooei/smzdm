package com.ppjun.android.smzdm.app

import android.app.Activity
import android.app.Application
import android.app.ApplicationErrorReport
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toolbar
import com.ppjun.android.smzdm.R
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
        if (activity!!.intent.getBooleanExtra("isInitToolbar", false).not()) {
            activity.intent.putExtra("isInitToolbar", true)
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

        activity.findViewById<TextView>(R.id.toolbarTitle)?.text = activity.title
        activity.findViewById<RelativeLayout>(R.id.toolBack)?.setOnClickListener {
            activity.onBackPressed()
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