package com.ppjun.android.smzdm.app.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.jess.arms.base.delegate.IActivity
import com.jess.arms.integration.cache.Cache
import com.jess.arms.integration.cache.CacheType
import com.jess.arms.integration.lifecycle.ActivityLifecycleable
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.utils.PreferencesUtil
import com.ppjun.android.smzdm.app.utils.StatusUtils
import com.ppjun.android.smzdm.app.utils.StatusUtils.Companion.StatusBarLightMode
import com.ppjun.android.smzdm.mvp.ui.activity.WebActivity
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.include_title.*
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
abstract class BaseUI<P : IPresenter> : AppCompatActivity(), IActivity, ActivityLifecycleable {

    protected val TAG = this.javaClass.simpleName
    val mLifecycleSubject: BehaviorSubject<ActivityEvent> = BehaviorSubject.create()
    var mCache: Cache<Any, Any>? = null
    var mUnbinder: Unbinder? = null
    @Inject
    lateinit var mPresenter: P
    lateinit var mCurrentTheme:String


    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE)
        }
        return mCache as Cache<String, Any>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCurrentTheme = PreferencesUtil.getString(this, Constant.THEME_COLOR)
        when (mCurrentTheme) {
            "mint" -> setTheme(R.style.AppThemeDark)
            else -> setTheme(R.style.AppTheme)
        }

        val layoutResID = initView(savedInstanceState)
        if (layoutResID != 0) {
            StatusBarLightMode(this)
            setContentView(layoutResID)
            setToolbarPadding()
            mUnbinder = ButterKnife.bind(this)
        }
        initData(savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
        val theme = PreferencesUtil.getString(this, Constant.THEME_COLOR)
        if (mCurrentTheme != theme) {
            recreate()
        }
    }

    fun setToolbarPadding() {
        toolbar?.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0)
    }

    fun setBackInvisible() {
        toolBack?.visibility = View.GONE
    }

    fun setToolBarGone() {
        toolbar?.visibility = View.GONE
    }


    override fun useFragment(): Boolean {
        return true
    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun provideLifecycleSubject(): Subject<ActivityEvent> {
        return mLifecycleSubject
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder?.unbind()
        }
        this.mUnbinder = null

    }

    fun toWebUI(url: String) {
        val result = Intent(this, WebActivity::class.java)
        result.putExtra(Constant.URL, url)
        startActivity(result)
    }

}