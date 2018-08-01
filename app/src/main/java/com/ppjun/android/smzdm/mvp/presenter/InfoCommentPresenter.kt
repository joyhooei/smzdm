package com.ppjun.android.smzdm.mvp.presenter

import android.app.Application
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.RxLifecycleUtils
import com.ppjun.android.smzdm.mvp.contract.InfoCommentContract
import com.ppjun.android.smzdm.mvp.model.entity.main.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject


@ActivityScope
class InfoCommentPresenter @Inject constructor(model: InfoCommentContract.Model, view: InfoCommentContract.View) :
        BasePresenter<InfoCommentContract.Model, InfoCommentContract.View>(model, view) {
    var isEmptyView = false
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
    var mAdapter: DefaultAdapter<InfoComment>? = null
    @Inject
    @JvmField
    var mRow: ArrayList<InfoComment>? = null
    private var offset = 0
    private var isFirst = true
    private var preEndIndex = 0


    fun requestCommentList(articleId: String, type: String, pullToRefresh: Boolean) {


        if (pullToRefresh) {
            offset = 0
        }

        var isEvictCache = pullToRefresh
        if (pullToRefresh && isFirst) {

            isFirst = true
            mRootView.hasLoadedAllItems(false)
            isEvictCache = false
        }
        mModel.commentList(articleId, type, offset)
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
                .subscribe(object : ErrorHandleSubscriber<Response<Data<InfoComment>>>(mErrorHandler) {
                    override fun onNext(result: Response<Data<InfoComment>>) {

                        if (pullToRefresh && result.data.rows.isEmpty()) {
                            //empty view
                            mRow?.clear()
                            isEmptyView = true
                            mAdapter?.notifyDataSetChanged()
                            mRootView.setEmptyView()
                            return
                        }
                        if (!pullToRefresh && result.data.rows.isEmpty()) {
                            // loaded all data
                            mRootView.endLoadMore()
                            mRootView.toastMessage("没有更多数据了")
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
                        if (pullToRefresh && result.data.rows.size < 10) {
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