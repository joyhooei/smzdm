package com.ppjun.android.smzdm.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Message
import android.support.multidex.MultiDex
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.BuildConfig
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.Constant
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.QbSdk
import timber.log.Timber
import com.ppjun.android.smzdm.mvp.ui.activity.MainActivity


class AppLifecyclesImpl : AppLifecycles {
    override fun attachBaseContext(base: Context?) {
        MultiDex.install(base)
    }

    override fun onCreate(application: Application) {
        val config = Configuration()
        config.setToDefaults()
        application.resources.updateConfiguration(config, application.resources.displayMetrics)//过时了，有效，推荐使用createConfigurationContext


        Beta.enableHotfix = false
        Bugly.init(application, Constant.BUGLY_ID, false)
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return
        }
        if (BuildConfig.LOG_DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ArmsUtils.obtainAppComponentFromContext(application).extras().put(RefWatcher::class.java.name,
                if (BuildConfig.USE_CANARY) LeakCanary.install(application) else RefWatcher.DISABLED)

        ArmsUtils.obtainAppComponentFromContext(application).appManager().handleListener = AppManager.HandleListener { appManager, message ->
            when (message?.what) {

            }
        }

        QbSdk.initX5Environment(application, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {

            }

            override fun onViewInitFinished(p0: Boolean) {
            }

        })
    }

    override fun onTerminate(application: Application?) {
    }
}