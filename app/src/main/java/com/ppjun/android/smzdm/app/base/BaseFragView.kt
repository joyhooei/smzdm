package com.ppjun.android.smzdm.app.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import butterknife.Unbinder
import com.jess.arms.base.delegate.IFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.cache.Cache
import com.jess.arms.integration.cache.CacheType
import com.jess.arms.integration.lifecycle.FragmentLifecycleable
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.ArmsUtils
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

abstract class BaseFragView<P : IPresenter> : Fragment(), IFragment, FragmentLifecycleable {
    protected val TAG = this.javaClass.simpleName
    val mLifecycleSubject: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()
    var mCache: Cache<Any, Any>? = null

    @Inject
    lateinit var mPresenter: P

    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(activity).cacheFactory().build(CacheType.ACTIVITY_CACHE)
        }
        return mCache as Cache<String, Any>
    }


    override fun useEventBus(): Boolean {
        return true
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater, container, savedInstanceState)
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent> {
        return mLifecycleSubject
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.onDestroy()
        }

    }

    protected fun closeKeyBoard(context: Activity) {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (manager!!.isActive && context.currentFocus != null) {
            if (context.currentFocus!!.windowToken != null) {
                manager.hideSoftInputFromWindow(context.currentFocus!!.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }


}