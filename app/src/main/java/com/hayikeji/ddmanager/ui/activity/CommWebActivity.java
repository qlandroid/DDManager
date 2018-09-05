package com.hayikeji.ddmanager.ui.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.ql.bindview.BindView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;

@BindLayout(layoutRes = R.layout.activity_comm_web)
public class CommWebActivity extends BaseActivity {

    @BindView(R.id.activity_web_view_wv)
    WebView webView;


    public static void putUrl(String url, String title, Bundle bundle) {
        bundle.putString("loadUrl", url);
        bundle.putString("title", title);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        Bundle bundle = getBundle();
        String title = bundle.getString("title");
        String loadUrl = bundle.getString("loadUrl");
        mTopBar.setTitle(title);
        webView.loadUrl(loadUrl);
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                         super.onPageStarted(view, url, favicon);

                                     }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         super.onPageFinished(view, url);
                                     }

                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String request) {
                                         view.loadUrl(request);

                                         return true;
                                     }

                                     @Override
                                     public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                                         super.onReceivedHttpError(view, request, errorResponse);
                                     }

                                     @Override
                                     public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                         handler.proceed();
                                     }

                                     @Override
                                     public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                         super.onReceivedError(view, request, error);
                                     }

                                     @Override
                                     public void onReceivedError(WebView view, int errorCode, String description, String
                                             failingUrl) {
                                         super.onReceivedError(view, errorCode, description, failingUrl);
                                     }
                                 }

        );
    }

    @Override
    public void clickTopbarLeftImgs() {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        clickTopbarLeftImgs();
    }

    @Override
    public void onClickKeyToBack() {
        super.onClickKeyToBack();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
