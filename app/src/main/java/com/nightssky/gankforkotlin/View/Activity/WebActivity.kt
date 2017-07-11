package com.nightssky.gankforkotlin.View.Activity

import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.nightssky.gankforkotlin.R
import kotlinx.android.synthetic.main.activity_web.*



class WebActivity : AppCompatActivity() {

    private var URL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            URL = bundle.getString("URL")
        }

        setSupportActionBar(tl_web)
        tl_web.setNavigationIcon(R.drawable.ic_back)
        initWebViewSettings()

        wv_content.removeJavascriptInterface("searchBoxJavaBridge_")
        wv_content.removeJavascriptInterface("accessibilityTraversal")
        wv_content.removeJavascriptInterface("accessibility")
        wv_content.loadUrl(URL)
    }

    public override fun onPause() {
        super.onPause()
        wv_content?.let { it.onPause() }
    }

    public override fun onResume() {
        super.onResume()
        wv_content?.let { it.onResume() }
    }

    public override fun onDestroy() {
        super.onDestroy()
        wv_content?.let { it.destroy() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.share -> {//share url with system share windows
                URL?.let { share(it) }
            }
            R.id.openinbrowse -> {
                URL?.let { browse(it) }
            }
            R.id.copyurl -> {
                val clipboardManager = getSystemService(
                        Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboardManager.text = URL
                Snackbar.make(tl_web, "已复制到剪切板", Snackbar.LENGTH_SHORT).show()
            }
            R.id.refresh -> {
                wv_content.reload()
            }
            else -> {
            }
        }
        return true
    }

    private fun initWebViewSettings() {
        val settings = wv_content.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        settings.savePassword = false
        wv_content.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressbar.progress = newProgress
                if (newProgress == 100) {
                    progressbar.visibility = View.GONE
                } else {
                    progressbar.visibility = View.VISIBLE
                }
            }


            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                setTitle(title)
            }
        })
        wv_content.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                url?.let { view.loadUrl(it) }
                return true
            }
        })
    }
}

fun Context.share(text: String, subject: String = ""): Boolean {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}