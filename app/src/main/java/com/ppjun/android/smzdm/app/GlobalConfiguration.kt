package com.ppjun.android.smzdm.app

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.module.GlobalConfigModule
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy
import com.jess.arms.integration.ConfigModule
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.mvp.model.api.Api.Companion.APP_DOMAIN
import com.squareup.leakcanary.RefWatcher
import me.jessyan.progressmanager.ProgressManager
import java.util.concurrent.TimeUnit

class GlobalConfiguration : ConfigModule {

    override fun applyOptions(context: Context?, builder: GlobalConfigModule.Builder?) {

        //  builder?.printHttpLogLevel(RequestInterceptor.Level.REQUEST)
        requireNotNull(builder).baseurl(APP_DOMAIN)
                .imageLoaderStrategy(GlideImageLoaderStrategy())
                .globalHttpHandler(GlobalHttpHandlerImpl(context!!))
                .responseErrorListener(ResponseErrorListenerImpl())
                .gsonConfiguration { _, builder ->
                    run {
                        builder.serializeNulls()
                                .enableComplexMapKeySerialization()
                    }
                }
                .okhttpConfiguration { _, builder ->
                    run {
                        builder.writeTimeout(10, TimeUnit.SECONDS)
                        ProgressManager.getInstance().with(builder)

                    }
                }
                .rxCacheConfiguration { _, builder ->
                    run {
                        builder.useExpiredDataIfLoaderNotAvailable(true)
                        return@run null
                    }

                }


    }

    override fun injectAppLifecycle(context: Context?, lifecycles: MutableList<AppLifecycles>?) {


        lifecycles?.add(AppLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context?, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>?) {

        lifecycles?.add(ActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(context: Context?, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>?) {

        lifecycles?.add(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
                //super.onFragmentCreated(fm, f, savedInstanceState)
                f?.retainInstance = true
            }

            override fun onFragmentDestroyed(fm: FragmentManager?, f: Fragment?) {
                // super.onFragmentDestroyed(fm, f)

                val watch = ArmsUtils.obtainAppComponentFromContext(f?.activity)
                        .extras()
                        .get(RefWatcher::class.java.name) as RefWatcher
                watch.watch(f)
            }
        })
    }

}