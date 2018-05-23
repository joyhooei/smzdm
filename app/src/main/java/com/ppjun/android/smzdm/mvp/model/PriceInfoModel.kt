package com.ppjun.android.smzdm.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.ppjun.android.smzdm.mvp.contract.PriceInfoContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceList
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject


@ActivityScope
class PriceInfoModel @Inject constructor(mRepositoryManager: IRepositoryManager) : BaseModel(mRepositoryManager)
        , PriceInfoContract.Model {




    override fun priceInfo(id: String, update: Boolean): Observable<Response<PriceInfo>> {
        return Observable.just(mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .priceInfo(id))
                .flatMap { t ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .priceInfo(t, DynamicKey(id), EvictDynamicKey(update))
                            .map { list ->
                                list.data
                            }
                }

    }


}