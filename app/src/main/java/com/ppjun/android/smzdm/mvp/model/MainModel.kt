package com.ppjun.android.smzdm.mvp.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.ppjun.android.smzdm.mvp.contract.MainContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.main.MainBanner
import com.ppjun.android.smzdm.mvp.model.entity.main.MainList
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import com.ppjun.android.smzdm.mvp.model.entity.main.RowData

import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import timber.log.Timber
import javax.inject.Inject


@FragmentScope
class MainModel @Inject constructor(mRepositoryManager:IRepositoryManager) :BaseModel(mRepositoryManager),MainContract.Model {
    override fun getMainBanner(page: Int, boolean: Boolean): Observable<Response<RowData<MainBanner>>> {
        return Observable.just(mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .getMainBanner())
                .flatMap { t ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .getMainBanner(t, DynamicKey(page), EvictDynamicKey(true))
                            .map { list ->
                                list.data
                            }
                }
    }


    val MAIN_PER_PAGE = 15


    override fun getMain(page: Int, update: Boolean): Observable<MainList> {
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