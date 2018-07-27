package com.ppjun.android.smzdm.mvp.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.ppjun.android.ppbannerview.PPBannerView
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.di.component.DaggerPriceInfoComponent
import com.ppjun.android.smzdm.di.module.PriceInfoModule
import com.ppjun.android.smzdm.mvp.contract.PriceInfoContract
import com.ppjun.android.smzdm.mvp.model.entity.Share
import com.ppjun.android.smzdm.mvp.model.entity.main.PriceInfo
import com.ppjun.android.smzdm.mvp.presenter.PriceInfoPresenter
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.ShareBottomSheetDialogFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.price_info_ui.*
import javax.inject.Inject


class PriceInfoActivity : BaseUI<PriceInfoPresenter>(), PriceInfoContract.View {
    var mShareFragment: ShareBottomSheetDialogFragment? = null
    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerPriceInfoComponent.builder()
                .appComponent(appComponent)
                .priceInfoModule(PriceInfoModule(this))
                .build()
                .inject(this)
    }

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

    @SuppressLint("JavascriptInterface")
    override fun showInfo(info: PriceInfo) {


        setContentView(R.layout.price_info_ui)
        priceInfoCommentLayout.setOnClickListener {
            val resultIntent=Intent(getTheActivity(),PriceCommentActivity::class.java)
            resultIntent.putExtra(Constant.ARTICLE_ID,info.articleId)
            resultIntent.putExtra(Constant.ARTICLE_COUNT,info.articleComment)
            resultIntent.putExtra(Constant.ARTICLE_TYPE,Constant.YOU_HUI)
            startActivity(resultIntent)
        }

        val images = ArrayList<String>()
        if (info.articleProductFocusPicUrl.isNotEmpty()) {
            for (image in info.articleProductFocusPicUrl) {
                images.add(image.pic)
            }
        } else {
            images.add(info.articlePic)
        }

        priceInfoBanner.setBannerData(images)

        priceInfoBanner.mOnBannerSwitchListener = object : PPBannerView.OnBannerSwitchListener {
            override fun onSwitch(position: Int, imageView: AppCompatImageView) {
                Glide.with(imageView.context)
                        .asBitmap()
                        .load(images[position])
                        .into(imageView)
                imageView.setOnClickListener {
                    val newIntent = Intent(imageView.context, PhotoUI::class.java)
                    newIntent.putExtra(Constant.KEY, images[position])
                    startActivity(newIntent)
                }
            }

        }
        priceInfoShop.visibility = View.VISIBLE
        priceInfoTime.visibility = View.VISIBLE
        priceInfoAuthor.visibility = View.VISIBLE
        priceInfoShop.text = if (info.articleMall.isEmpty()) getString(R.string.title) else info.articleMall
        priceInfoTime.text = info.articleFormatDate
        priceInfoAuthor.text = String.format(getString(R.string.author),info.articleReferrals)
        priceInfoTitle.text = info.articleTitle
        priceInfoPrice.text = info.articlePrice
        if (info.articlePhraseDesc.isEmpty()) {
            priceInfoDesc.height = 0
        }
        priceInfoCommentText.text=info.articleComment
        priceInfoDesc.text = info.articlePhraseDesc
        priceInfoWorth.text = info.articleWorthy
        priceInfoUnworth.text = info.articleUnworthy
        priceInfoBuy.setOnClickListener {
            toWebUI(info.articleLink)
        }

        val webSettings = priceInfoWeb.settings

        priceInfoWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                toWebUI(url!!)
                return true
            }
        }
        val data = "<!DOCTYPE HTML><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/editor.css\" /></head><body>" + info.productIntro + info.articleContent + info.articleBlReason + "</body></html>"
        priceInfoWeb.loadDataWithBaseURL(null, data, "text/html;charset=utf-8", "utf-8", null)


        priceInfoShareImg.setOnClickListener {
            share(info)
        }


    }


    fun share(info: PriceInfo) {
        mShareFragment = ShareBottomSheetDialogFragment()
                .apply {
                    val bundle = Bundle()
                    bundle.putParcelable(
                            Constant.KEY,
                            Share(info.articleTitle,
                                    info.articlePrice + info.articlePhraseDesc,
                                    info.articleLink,
                                    info.articleSmallPic))
                    arguments = bundle
                    show(supportFragmentManager, "tag")
                }
    }

    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent?) {
    }

    override fun hideLoading() {
    }

    override fun killMyself() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mShareFragment = null
    }

    override fun showMessage(message: String?) {
        ArmsUtils.snackbarText(message)
    }

    override fun initData(savedInstanceState: Bundle?) {

        mPresenter.requestPriceInfo(intent.getStringExtra(Constant.ID))
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        intent.putExtra("isInitToolbar", true)
        return R.layout.loading
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mShareFragment?.onQQActivityResult(requestCode, resultCode, data)
    }

}