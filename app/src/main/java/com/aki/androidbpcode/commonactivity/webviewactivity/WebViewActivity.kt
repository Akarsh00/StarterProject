package com.aki.androidbpcode.commonactivity.webviewactivity

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.aki.androidbpcode.R
import com.aki.androidbpcode.base.BaseActivity
import com.aki.androidbpcode.viewmodel.TestViewModel


class WebViewActivity(private val url: String = "https://github.com/ChrisRisner/Android-AzureActiveDirectory/blob/master/source/src/com/cmr/waad_test/Constants.java") :
    BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = setUpWebView()
        setContentView(webView)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                hideProgessDialog()
            }
        }


    }

    private fun setUpWebView(): WebView {
        val webView = WebView(this)
        val webViewClient =
            AppWebViewClient()
        webView.webViewClient = webViewClient
        val settings = webView.settings
        settings.javaScriptEnabled = true
        showProgressDialog()
        webView.loadUrl(
            url
        )
        return webView
    }

    class AppWebViewClient() : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return false
        }
    }

    override val view: Int
        get() = R.layout.activity_web_view

}