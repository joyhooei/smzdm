package com.ppjun.android.smzdm.mvp.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.LogUtils
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.app.base.Constant.Companion.JD_PACKAGE_NAME
import com.ppjun.android.smzdm.app.base.Constant.Companion.OPEN_JD_PREFIX
import com.ppjun.android.smzdm.app.base.Constant.Companion.OPEN_TB_PREFIX
import com.ppjun.android.smzdm.app.base.Constant.Companion.TAOBAO_PACKAGE_NAME
import com.ppjun.android.smzdm.app.utils.ApkUtils
import com.ppjun.android.smzdm.app.utils.StatusUtils
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.web_ui.*

class WebActivity : BaseUI<IPresenter>() {
    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.web_ui
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolSearch.visibility=View.GONE
        toolbar.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0)
        toolBack.visibility = View.VISIBLE

        x5WebView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = true
            databaseEnabled = true
            domStorageEnabled = true
            setGeolocationEnabled(true)
            setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
            useWideViewPort = true
            setSupportZoom(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            pluginState = WebSettings.PluginState.ON_DEMAND
            cacheMode = WebSettings.LOAD_NO_CACHE
        }
        x5WebView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return if (url!!.startsWith("http:").not() || url.startsWith("https:").not()) {
                    LogUtils.debugInfo("debug=",url)
                    openApp(url)
                    false
                } else {
                    view?.loadUrl(url)
                    LogUtils.debugInfo("url=", url)
                    true
                }
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
            }
            override fun onReceivedSslError(p0: WebView?, p1: SslErrorHandler?, p2: SslError?) {
                p1?.proceed()
            }
        })
        progress.show()
        x5WebView.setDownloadListener(object : DownloadListener {
            override fun onDownloadStart(url: String?, p1: String?, p2: String?, p3: String?, p4: Long) {
                var result = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(result)
            }

        })
        x5WebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
                progress.progress = p1
                LogUtils.debugInfo(p1.toString())
                if (p1 == 100) {
                    progress.hide()
                }
            }


            override fun onReceivedTitle(p0: WebView?, p1: String?) {
                super.onReceivedTitle(p0, p1)
                toolbarTitle.text = p1
                LogUtils.debugInfo("p1=", p1)
            }
        })
        val url = intent.getStringExtra(Constant.URL)
        if (url.isNullOrEmpty().not()) {
            x5WebView.loadUrl(url)
        }

    }

    private fun openApp(url: String) {

        when(url.split("://")[0]){
            OPEN_JD_PREFIX->{
                link2App(JD_PACKAGE_NAME,url)
            }
            OPEN_TB_PREFIX->{
                link2App(TAOBAO_PACKAGE_NAME,url)
            }
        }

    }


   private  fun link2App(packageName:String,url:String){
        if(ApkUtils.isInstall(packageName)){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        x5WebView.destroy()

    }
}