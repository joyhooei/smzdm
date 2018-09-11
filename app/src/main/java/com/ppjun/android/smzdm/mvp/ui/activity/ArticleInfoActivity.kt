package com.ppjun.android.smzdm.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.request.RequestOptions
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.ppjun.android.ppbannerview.PPBannerView
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.R.string.share
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.di.component.DaggerArticleInfoComponent
import com.ppjun.android.smzdm.di.module.ArticleInfoModule
import com.ppjun.android.smzdm.mvp.contract.ArticleInfoContract
import com.ppjun.android.smzdm.mvp.model.entity.Share
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleInfo
import com.ppjun.android.smzdm.mvp.presenter.ArticleInfoPresenter
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.ShareBottomSheetDialogFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.article_info_ui.*
import kotlinx.android.synthetic.main.price_info_ui.*
import javax.inject.Inject


class ArticleInfoActivity : BaseUI<ArticleInfoPresenter>(), ArticleInfoContract.View {
    var mShareFragment: ShareBottomSheetDialogFragment? = null

    @Inject
    lateinit var mRxPermissions: RxPermissions


    override fun startLoadMore() {

    }

    override fun endLoadMore() {
    }

    override fun getTheActivity(): Activity {
        return this
    }

    override fun getRxPermission(): RxPermissions {
        return mRxPermissions
    }


    fun share(info: ArticleInfo) {
        mShareFragment = ShareBottomSheetDialogFragment()
                .apply {
                    val bundle = Bundle()
                    bundle.putParcelable(
                            Constant.KEY,
                            Share(info.articleTitle,
                                    "",
                                    info.articleUrl,
                                    info.articleSmallPic))
                    arguments = bundle

                    show(supportFragmentManager,"")
                }
    }


    override fun showInfo(info: ArticleInfo) {
        setContentView(R.layout.article_info_ui)
        articleInfoCollectText.text = intent.getStringExtra(Constant.COLLECT)
        articleInfoCommentText.text = intent.getStringExtra(Constant.COMMENT)
        articleInfoComment.setOnClickListener {
            val resultIntent=Intent(getTheActivity(),PriceCommentActivity::class.java)
            resultIntent.putExtra(Constant.ARTICLE_ID,info.articleId)
            resultIntent.putExtra(Constant.ARTICLE_COUNT,info.articleComment)
            resultIntent.putExtra(Constant.ARTICLE_TYPE,Constant.YUAN_CHUANG)
            startActivity(resultIntent)
        }

        articleInfoTop.setOnClickListener {
            articleInfoScroll.smoothScrollTo(0, 0)
        }
        articleInfoShareImg.setOnClickListener {
            share(info)
        }
        val images = ArrayList<String>()
        images.add(info.articlePic)
        articleInfoBanner.setBannerData(images)

        articleInfoBanner.mOnBannerSwitchListener = object : PPBannerView.OnBannerSwitchListener {
            override fun onSwitch(position: Int, imageView: AppCompatImageView) {
                ArmsUtils.obtainAppComponentFromContext(this@ArticleInfoActivity).imageLoader().loadImage(this@ArticleInfoActivity, ImageConfigImpl.builder()
                        .url(images[position])
                        .imageView(imageView)
                        .build())
            }

        }
        GlideArms.with(this)
                .load(info.articleAvatar)
                .circleCrop()
                .into(articleInfoAuthorImg)
        articleInfoAuthor.text = info.articleReferrals
        articleInfoTime.text = info.articleFormatDate
        articleInfoTitle.text = info.articleTitle

        articleInfoWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                toWebUI(url!!)
                return true
            }
        }
        val data = "<!DOCTYPE HTML><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/editor.css\" /></head><body>" + info.articleFilterContent + "</body></html>";
        articleInfoWeb.loadDataWithBaseURL(null, data, "text/html;charset=utf-8", "utf-8", null)
    }

    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String) {
    }

    override fun initData(savedInstanceState: Bundle?) {

        mPresenter.requestPriceInfo(intent.getStringExtra(Constant.ID))

    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerArticleInfoComponent.builder()
                .appComponent(appComponent)
                .articleInfoModule(ArticleInfoModule(this))
                .build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        intent.putExtra("isInitToolbar", true)
        return R.layout.loading
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mShareFragment?.onQQActivityResult(requestCode, resultCode, data)
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        LogUtils.debugInfo("debug=","info onSaveInstanceStateonSaveInstanceState")
    }
}