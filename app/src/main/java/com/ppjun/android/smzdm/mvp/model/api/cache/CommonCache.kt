package com.ppjun.android.smzdm.mvp.model.api.cache

import com.ppjun.android.smzdm.mvp.model.entity.Response
import com.ppjun.android.smzdm.mvp.model.entity.main.MainList
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.model.entity.main.TestBean
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.LifeCache
import io.rx_cache2.Reply
import java.util.concurrent.TimeUnit

interface CommonCache {
    @LifeCache(timeUnit = TimeUnit.MINUTES,duration = 2)
    fun getMainList(response: Observable<TestBean>, page: DynamicKey, evictProvider:EvictDynamicKey)
   : Observable<Reply<TestBean>>
}