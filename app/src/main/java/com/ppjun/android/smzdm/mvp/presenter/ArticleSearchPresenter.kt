package com.ppjun.android.smzdm.mvp.presenter

import android.app.Application
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.AppManager
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.ppjun.android.smzdm.mvp.contract.ArticleSearchContract
import com.ppjun.android.smzdm.mvp.model.entity.main.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject


@FragmentScope
class ArticleSearchPresenter @Inject constructor(model: ArticleSearchContract.Model
                                                 , view: ArticleSearchContract.View) : BasePresenter<ArticleSearchContract.Model, ArticleSearchContract.View>(model, view) {

    @Inject
    @JvmField
    var mErrorHandler: RxErrorHandler? = null
    @Inject
    @JvmField
    var mAppManager: AppManager? = null
    @Inject
    @JvmField
    var mApplication: Application? = null
    @Inject
    @JvmField
    var mAdapter: DefaultAdapter<ArticleData>? = null
    @Inject
    @JvmField
    var mRow: ArrayList<ArticleData>? = null
    private var offset = 20
    private var isFirst = true
    private var preEndIndex = 0


    fun requestArticleList(keyword: String, pullToRefresh: Boolean) {


        if (pullToRefresh) {
            offset = 0
        }

        var isEvictCache = pullToRefresh
        if (pullToRefresh && isFirst) {
            mRootView?.moveRVToTop()
            isFirst = true
            mRootView.hasLoadedAllItems(false)
            isEvictCache = false
        }
        mModel.articleList(keyword,offset, true)
                .subscribeOn(Schedulers.io())
                .retryWhen(
                        RetryWithDelay(3, 2)
                )
                .doOnSubscribe { disposible ->
                    if (pullToRefresh) {
                        mRootView.showLoading()
                    } else {
                        mRootView.startLoadMore()
                    }
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (pullToRefresh) {
                        mRootView.hideLoading()
                    } else {
                        mRootView.endLoadMore()
                    }
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<Response<ArticleSearch<ArticleData>>>(mErrorHandler) {
                    override fun onNext(result: Response<ArticleSearch<ArticleData>>) {

                        if(pullToRefresh&&result.data.rows.isEmpty()){
                            //empty view
                            mRow?.clear()
                            mAdapter?.notifyDataSetChanged()
                            mRootView.setEmptyView()
                            return
                        }
                        if(!pullToRefresh&&result.data.rows.isEmpty()){
                            // loaded all data
                            mRootView.endLoadMore()
                            mRootView.showMessage("没有更多数据了")
                            mRootView.hasLoadedAllItems(true)
                            return
                        }
                        offset += 20
                        if (pullToRefresh) {

                            mRow?.clear()
                        }
                        preEndIndex = requireNotNull(mRow?.size)
                        mRow?.addAll(result.data.rows)
                        if (pullToRefresh) {

                            mAdapter?.notifyDataSetChanged()
                        } else {
                            mAdapter?.notifyItemRangeInserted(preEndIndex, result.data.rows.size)
                        }
                        if(pullToRefresh&&result.data.rows.size<10){
                            mRootView.endLoadMore()
                            mRootView.hasLoadedAllItems(true)
                        }
                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mAdapter = null
        this.mRow = null
        this.mErrorHandler = null
        this.mAppManager = null
        this.mApplication = null
    }

}