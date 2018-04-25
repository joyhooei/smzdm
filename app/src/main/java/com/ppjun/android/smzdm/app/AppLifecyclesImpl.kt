package com.ppjun.android.smzdm.app

import android.app.Application
import android.content.Context
import android.os.Message
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.BuildConfig
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.QbSdk
import timber.log.Timber

class AppLifecyclesImpl : AppLifecycles {
    override fun attachBaseContext(base: Context?) {

    }

    override fun onCreate(application: Application?) {

        CrashReport.initCrashReport(application, "29e07ce703", true)

        if (LeakCanary.isInAnalyzerProcess(application)) {
            return
        }
        if (BuildConfig.LOG_DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ArmsUtils.obtainAppComponentFromContext(application).extras().put(RefWatcher::class.java.name,
                if (BuildConfig.USE_CANARY) LeakCanary.install(application) else RefWatcher.DISABLED)

        ArmsUtils.obtainAppComponentFromContext(application).appManager().handleListener = AppManager.HandleListener {
            appManager, message ->
            when(message?.what){

            }
        }

        QbSdk.initX5Environment(application,object:QbSdk.PreInitCallback{
            override fun onCoreInitFinished() {

            }

            override fun onViewInitFinished(p0: Boolean) {
            }

        })
    }

    override fun onTerminate(application: Application?) {
    }
}