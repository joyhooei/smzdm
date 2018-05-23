package com.ppjun.android.smzdm.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.ppjun.android.smzdm.mvp.contract.ArticleInfoContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject

@ActivityScope
class ArticleInfoModel @Inject constructor(mRepositoryManager:IRepositoryManager):BaseModel(mRepositoryManager),
ArticleInfoContract.Model{
    override fun articleInfo(id: String, update: Boolean): Observable<Response<ArticleInfo>> {
            return Observable.just(mRepositoryManager.obtainRetrofitService(MainService::class.java)
                    .articleInfo(id))
                    .flatMap { t ->
                        mRepositoryManager.obtainCacheService(CommonCache::class.java)
                                .articleInfo(t, DynamicKey(id), EvictDynamicKey(update))
                                .map { list ->
                                    list.data
                                }
                    }

        }



}