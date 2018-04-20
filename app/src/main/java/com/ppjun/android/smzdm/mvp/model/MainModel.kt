package com.ppjun.android.smzdm.mvp.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.integration.RepositoryManager
import com.jess.arms.mvp.BaseModel
import com.ppjun.android.smzdm.mvp.contract.MainContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.Response
import com.ppjun.android.smzdm.mvp.model.entity.main.MainList
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.model.entity.main.Rows
import com.ppjun.android.smzdm.mvp.model.entity.main.TestBean
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import timber.log.Timber
import javax.inject.Inject


@FragmentScope
class MainModel @Inject constructor(mRepositoryManager:IRepositoryManager) :BaseModel(mRepositoryManager),MainContract.Model {


    val MAIN_PER_PAGE = 15


    override fun getMain(page: Int, update: Boolean): Observable<TestBean> {
        return Observable.just(mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getMain(page, MAIN_PER_PAGE))
                .flatMap { t ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .getMainList(t, DynamicKey(page), EvictDynamicKey(update))
                            .map { list ->
                                list.data
                            }
                }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.d("releases resource")

    }
}