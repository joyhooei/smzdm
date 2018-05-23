package com.ppjun.android.smzdm.mvp.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.ppjun.android.smzdm.mvp.contract.ArticleListContract
import com.ppjun.android.smzdm.mvp.contract.PriceContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleList
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceList
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject

@FragmentScope
class ArticleListModel  @Inject constructor(mRepositoryManager: IRepositoryManager) : BaseModel(mRepositoryManager), ArticleListContract.Model{

    val MAIN_PER_PAGE = 15
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {


    }

    override fun articleList(page: Int, update: Boolean): Observable<ArticleList> {

        return Observable.just(mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .articleList(page, MAIN_PER_PAGE))
                .flatMap { t ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .articleList(t, DynamicKey(page), EvictDynamicKey(update))
                            .map { list ->
                                list.data
                            }
                }

    }
}