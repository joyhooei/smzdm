package com.ppjun.android.smzdm.mvp.model

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.jess.arms.utils.LogUtils
import com.ppjun.android.smzdm.mvp.contract.InfoCommentContract
import com.ppjun.android.smzdm.mvp.model.api.cache.CommonCache
import com.ppjun.android.smzdm.mvp.model.api.service.MainService
import com.ppjun.android.smzdm.mvp.model.entity.main.InfoComment
import com.ppjun.android.smzdm.mvp.model.entity.main.Response
import com.ppjun.android.smzdm.mvp.model.entity.main.Data
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import javax.inject.Inject


@ActivityScope
class InfoCommentModel @Inject constructor(mRepositoryManager: IRepositoryManager) : BaseModel(mRepositoryManager), InfoCommentContract.Model {
    override fun commentList(articleId: String, type: String, page: Int): Observable<Response<Data<InfoComment>>> {
        LogUtils.debugInfo("debug=",articleId)
        LogUtils.debugInfo("debug=",type)
        LogUtils.debugInfo("debug=",page.toString())
        return Observable.just(mRepositoryManager.obtainRetrofitService(MainService::class.java)
                .infoComment(articleId, type, page))
                .flatMap { t ->
                    mRepositoryManager.obtainCacheService(CommonCache::class.java)
                            .infoComment(t, DynamicKey(page), EvictDynamicKey(true))
                            .map { list ->
                                list.data
                            }
                }
    }


}