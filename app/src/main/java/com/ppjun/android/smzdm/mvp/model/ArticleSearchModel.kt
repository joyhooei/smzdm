package com.ppjun.android.smzdm.mvp.model

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.contract.ArticleSearchContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleData
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleList
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleSearch
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject

@FragmentScope
class ArticleSearchModel @Inject constructor(var repo: IRepositoryManager) : BaseModel(repo),
        ArticleSearchContract.Model {
    override fun articleList(keyword: String, page: Int, update: Boolean): Observable<Response<ArticleSearch<ArticleData>>> {
        return Observable.just(repo.obtainRetrofitService(MainService::class.java)
                .articleSearch(keyword, page, Constant.MAIN_PER_PAGE))
                .flatMap { t ->

                    repo.obtainCacheService(CommonCache::class.java)
                            .articleSearch(t, DynamicKey(page), EvictDynamicKey(update))
                            .map { list ->
                                list.data

                            }
                }
    }
}