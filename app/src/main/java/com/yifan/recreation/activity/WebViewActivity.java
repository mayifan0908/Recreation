package com.yifan.recreation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yifan.recreation.BaseActivity;
import com.yifan.recreation.R;
import com.yifan.recreation.view.CustomWebView;

import java.net.URISyntaxException;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class WebViewActivity extends BaseActivity implements CustomWebView.JSInterface {
    private CustomWebView webView;
    private String url;
    private ProgressBar progressBar;
    private FloatingActionButton fabClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_web_view);
        url = getIntent().getStringExtra("url");
        isShowToolbar = getIntent().getBooleanExtra("isShowToolbar", true);
        initView();
        initData();
    }

    public void updateProgress(int progress) {
        if (progress == progressBar.getMax() || progress == 0) {
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        progressBar.setProgress(progress);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //初始化UI控件
    @SuppressLint("JavascriptInterface")
    private void initView() {
        fabClose = (FloatingActionButton) findViewById(R.id.fabClose);
        if (isShowToolbar) {
            fabClose.setVisibility(View.GONE);
        } else {
            fabClose.setVisibility(View.VISIBLE);
        }
        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.bringToFront();
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setIndeterminate(false);
        webView = (CustomWebView) findViewById(R.id.webView);

        webView = (CustomWebView) findViewById(R.id.webView);
        webView.setCurrentActiviy(this);
        webView.addJavascriptInterface(this, "PrayerJS");

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.setWebViewClient(webViewClient);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbar.setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                WebViewActivity.this.updateProgress(progress);
            }


        });
        webView.loadUrl(url);
    }

    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Uri uri = Uri.parse(url);

            if (uri.getScheme().equals("http") || uri.getScheme().equals("https")) {
                return false;
            }
            //其他协议
            try {
                Intent intent = Intent.parseUri(url, 0);
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return true;
        }
    };

    //初始化数据
    private void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            hideSoftKeyboard();
            super.onBackPressed();
        }
    }

    @Override
    public void hideSoftKeyboard() {
        webView.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(webView.getWindowToken(), 0);
    }

    @JavascriptInterface
    public void close() {
        WebViewActivity.this.finish();
    }

    @JavascriptInterface
    public void onOpenUrl(String url) {

    }

    @JavascriptInterface
    public void previewImage(String urls, String current) {
//打开图片
    }

    @JavascriptInterface
    public void setTitle(String back, int type, String callBack) {

    }

    @JavascriptInterface
    public void setRightTitle(String text, String callBack) {

    }

    @JavascriptInterface
    public String getUserInfo() {
        return null;
    }
}
