package com.example.wordsfactory.presentation.video

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.ui.theme.Primary
import org.koin.androidx.compose.koinViewModel


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoScreen(viewModel: VideoViewModel = koinViewModel()) {
    val state by viewModel.videoState.collectAsStateWithLifecycle()
//    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
//    var progress by remember { mutableIntStateOf(0) }
    val activity = LocalView.current.context as Activity
    Box {
        AndroidView(factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?, request: WebResourceRequest?
                    ): Boolean {
                        return request?.url?.host != Constants.VIDEO_HOST
                    }

                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        viewModel.onBackEnabledChanged(view.canGoBack())
//                        backEnabled = view.canGoBack()
                        super.onPageStarted(view, url, favicon)
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    var customView: View? = null
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        viewModel.onProgressChanged(newProgress)
                    }

                    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                        super.onShowCustomView(view, callback)
                        if (this.customView != null) {
                            onHideCustomView()
                            return
                        }
                        this.customView = view
                        (activity.window.decorView as FrameLayout).addView(
                            this.customView, FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        )
                    }

                    override fun onHideCustomView() {
                        super.onHideCustomView()
                        (activity.window.decorView as FrameLayout).removeView(this.customView)
                        this.customView = null
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(Constants.VIDEO_URL)
                webView = this
            }
        }, update = {
            webView = it
        })
        BackHandler(enabled = state.backEnabled) {
            webView?.goBack()
        }
        if (state.progress in 1..99) LinearProgressIndicator(
            progress = { state.progress / 100f },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            color = Primary,
            trackColor = Primary.copy(alpha = 0.2f)
        )
    }
}