package com.ppjun.android.smzdm.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.ppjun.android.ppbannerview.PPBannerView
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.di.component.DaggerNewsInfoComponent
import com.ppjun.android.smzdm.di.module.NewsInfoModule
import com.ppjun.android.smzdm.mvp.contract.NewsInfoContract
import com.ppjun.android.smzdm.mvp.model.entity.Share
import com.ppjun.android.smzdm.mvp.model.entity.main.ArticleInfo
import com.ppjun.android.smzdm.mvp.model.entity.main.NewsInfo
import com.ppjun.android.smzdm.mvp.presenter.NewsInfoPresenter
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.ShareBottomSheetDialogFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.article_info_ui.*
import kotlinx.android.synthetic.main.news_info_ui.*
import kotlinx.android.synthetic.main.price_info_ui.*
import javax.inject.Inject

class NewsInfoActivity : BaseUI<NewsInfoPresenter>(), NewsInfoContract.View {
    var mShareFragment: ShareBottomSheetDialogFragment? = null

    @Inject
    lateinit var mRxPermissions: RxPermissions

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter.requestNewsInfo(intent.getStringExtra(Constant.ID))
    }

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerNewsInfoComponent.builder()
                .appComponent(appComponent)
                .newsInfoModule(NewsInfoModule(this))
                .build().inject(this)

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        intent.putExtra("isInitToolbar", true)
        return R.layout.loading
    }

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


    override fun onPostResume() {
        super.onPostResume()


    }

    fun share(info: NewsInfo) {
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
                    show(supportFragmentManager, "tag")
                }
    }

    override fun showInfo(info: NewsInfo) {
        newsInfoComment.setOnClickListener {
            val resultIntent = Intent(getTheActivity(), PriceCommentActivity::class.java)
            resultIntent.putExtra(Constant.ARTICLE_ID, info.articleId)
            resultIntent.putExtra(Constant.ARTICLE_COUNT, info.articleComment)
            resultIntent.putExtra(Constant.ARTICLE_TYPE, Constant.NEWS)
            startActivity(resultIntent)

        }

        setContentView(R.layout.news_info_ui)
        newsInfoCommentText.text = intent.getStringExtra(Constant.COMMENT)
        newsInfoLoveountText.text = intent.getStringExtra(Constant.COLLECT)
        newsInfoTop.setOnClickListener {
            newsInfoScroll.smoothScrollTo(0, 0)

        }
        newsInfoShareImg.setOnClickListener {
            share(info)
        }


        val images = ArrayList<String>()

        images.add(info.articlePic)


        newsInfoBanner.setBannerData(images)

        newsInfoBanner.mOnBannerSwitchListener = object : PPBannerView.OnBannerSwitchListener {
            override fun onSwitch(position: Int, imageView: AppCompatImageView) {
                ArmsUtils.obtainAppComponentFromContext(this@NewsInfoActivity).imageLoader().loadImage(this@NewsInfoActivity, ImageConfigImpl.builder()
                        .url(images[position])
                        .imageView(imageView)
                        .build())
            }

        }
        GlideArms.with(this)
                .load(info.articleAvatar)
                .circleCrop()
                .into(newsInfoAuthorImg)
        newsInfoAuthor.text = info.articleFormatDate
        //newsInfoTime.text=info.articleFormatDate
        newsInfoTitle.text = info.articleTitle


        newsInfoWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                toWebUI(requireNotNull(url))
                return true
            }


        }
        val data = "<!DOCTYPE HTML><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/editor.css\" /></head><body>" + info.articleFilterContent + "</body></html>";
        newsInfoWeb.loadDataWithBaseURL(null, data, "text/html;charset=utf-8", "utf-8", null)


    }

    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent?) {
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
    }

    override fun showMessage(message: String?) {
        ArmsUtils.snackbarText(message)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mShareFragment?.onQQActivityResult(requestCode, resultCode, data)

    }
}