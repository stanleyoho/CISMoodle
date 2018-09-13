package com.app.cismoodle.ui.activity

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import android.widget.Toast
import com.app.cismoodle.R
import com.app.cismoodle.consts.Constants
import com.app.cismoodle.ui.dialog.DialogLoading
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActicity() {

    private lateinit var loadDialog : DialogLoading
    private var myWebView : MyWebView? = null
    private var firstPressedTime : Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initWebView()
        initEvent()
    }

    override fun onDestroy() {
        if (myWebView != null) {
            myWebView?.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            myWebView?.clearHistory()
            (myWebView?.parent as ViewGroup).removeView(myWebView)
            myWebView?.destroy()
            myWebView = null
        }
        super.onDestroy()
    }

    override fun initEvent(){
        btnHome.setOnClickListener { myWebView?.loadUrl(Constants.CAI_HOST) }
        btnMoodle.setOnClickListener { myWebView?.loadUrl(Constants.CAI_MOODLE) }
    }

    override fun initView(){
        setContentView(R.layout.activity_main)
        loadDialog = DialogLoading(this)
    }

    private fun  initWebView(){
        myWebView = MyWebView(applicationContext)
        myWebView?.webChromeClient = myChromeClient
        myWebView?.webViewClient = myWebClient
        myWebView?.loadUrl(Constants.CAI_HOST)

        val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

        myWebView?.layoutParams = layoutParams
        layoutMain.addView(myWebView)
    }

    override fun onBackPressed() {
        if(myWebView!!.canGoBack()){
            myWebView?.goBack()
        }else{
            if(System.currentTimeMillis() - firstPressedTime < 2000){
                super.onBackPressed()
            }else{
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show()
                firstPressedTime = System.currentTimeMillis()
            }
        }
    }

    class MyWebView(context: Context?) : WebView(context) {
        init {
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode =true
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.domStorageEnabled = true
        }
    }

    private var myWebClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view?.loadUrl(request?.url.toString())
            }
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            loadDialog.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            loadDialog.dismiss()
        }
    }

    private var myChromeClient = object : WebChromeClient(){

    }
}
