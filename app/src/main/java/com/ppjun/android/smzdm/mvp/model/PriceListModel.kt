package com.ppjun.android.smzdm.mvp.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.ppjun.android.smzdm.app.base.Constant.Companion.MAIN_PER_PAGE
import com.ppjun.android.smzdm.mvp.contract.PriceContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceList
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject


@FragmentScope
class PriceListModel @Inject constructor(mRepositoryManager: IRepositoryManager) : BaseModel(mRepositoryManager), PriceContract.Model {


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {


    }

    override fun priceList(page: Int, update: Boolean): Observable<PriceList> {

        return Observable.just(mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .priceList(page, MAIN_PER_PAGE))
                .flatMap { t ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .priceList(t, DynamicKey(page), EvictDynamicKey(update))
                            .map { list ->
                                list.data
                            }
                }

    }
}