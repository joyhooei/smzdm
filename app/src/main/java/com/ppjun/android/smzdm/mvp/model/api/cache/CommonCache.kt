package com.ppjun.android.smzdm.mvp.model.api.cache

import com.ppjun.android.smzdm.mvp.model.entity.main.*
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.LifeCache
import io.rx_cache2.Reply
import java.util.concurrent.TimeUnit

interface CommonCache {
    @LifeCache(timeUnit = TimeUnit.MINUTES, duration = 2)
    fun getMainList(response: Observable<MainList>, page: DynamicKey, evictProvider: EvictDynamicKey)
            : Observable<Reply<MainList>>


    @LifeCache(timeUnit = TimeUnit.MINUTES, duration = 2)
    fun priceList(response: Observable<PriceList>, page: DynamicKey, evictProvider: EvictDynamicKey)
            : Observable<Reply<PriceList>>


    @LifeCache(timeUnit = TimeUnit.MINUTES, duration = 2)
    fun articleList(response: Observable<ArticleList>, page: DynamicKey, evictProvider: EvictDynamicKey)
            : Observable<Reply<ArticleList>>


    @LifeCache(timeUnit = TimeUnit.MINUTES, duration = 2)
    fun priceInfo(response: Observable<Response<PriceInfo>>, page: DynamicKey, evictProvider: EvictDynamicKey)
            : Observable<Reply<Response<PriceInfo>>>

    @LifeCache(timeUnit = TimeUnit.MINUTES, duration = 2)
    fun articleInfo(response: Observable<Response<ArticleInfo>>, page: DynamicKey, evictProvider: EvictDynamicKey)
            : Observable<Reply<Response<ArticleInfo>>>

    @LifeCache(timeUnit = TimeUnit.MINUTES, duration = 2)
    fun newsInfo(response: Observable<Response<NewsInfo>>, page: DynamicKey, evictProvider: EvictDynamicKey)
            : Observable<Reply<Response<NewsInfo>>>
}