package com.ppjun.android.smzdm.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.contract.PriceSearchContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceList
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject


@FragmentScope
class PriceSearchModel @Inject constructor(var repo: IRepositoryManager) : BaseModel(repo), PriceSearchContract.Model {


    override fun priceList(keyword: String, page: Int, update: Boolean): Observable<PriceList> {
        return Observable.just(repo.obtainRetrofitService(MainService::class.java)
                .priceSearch(keyword, page, Constant.MAIN_PER_PAGE))
                .flatMap { t ->
                    repo.obtainCacheService(CommonCache::class.java)
                            .priceSearch(t, DynamicKey(page), EvictDynamicKey(update))
                            .map { list ->
                                list.data
                            }
                }
    }
}