package com.soyinka.soyombo.naijaplusworldnews;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private static ProgressBar progressBar;
    private static View transaperentView;
    private Bundle b;

    //Navigation arrow on the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        if (id == R.id.action_reset){
            if (b != null){
                url = b.getString("Url");
            }
            setUpWebViewDefault(webView);
            webView.loadUrl(url);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null){
            webView.clearCache(true);
            webView.destroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (b != null){
            b.putString("Url", url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (b != null){
            url = b.getString("Url");
            setUpWebViewDefault(webView);
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.website_view);
        transaperentView = findViewById(R.id.view);
        progressBar = findViewById(R.id.progress_bar);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544-3347511713");
        AdView mAdView = findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());

        //Navigation arrow on the acton bar; check also override onOptionsItemSelected
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null){
            Intent intent = getIntent();
            if (intent != null){
                url = intent.getStringExtra("Website");
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                if (!url.contains("https://")){
                    url = "https://" + url;
                } else if (url.equals("thenatonlineng.net") || url.contains("thenatonlineng.net")){
                    url = "http://" + url;
                }
            }

            setUpWebViewDefault(webView);
            Toast.makeText(this, "" + url, Toast.LENGTH_LONG).show();
            webView.loadUrl(url);
        } else {
            url = savedInstanceState.getString("Url");
            setUpWebViewDefault(webView);
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Url", url);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        webView.clearCache(true);
//        webView.destroy();
//    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setUpWebViewDefault(WebView webViewDefault){
        webViewDefault.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = webViewDefault.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
            webSettings.setDisplayZoomControls(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            webViewDefault.setWebContentsDebuggingEnabled(true);
        }
    }

    public static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (!url.contains("https://")){
                view.loadUrl("https://" + url);
            } else if (url.contains("saharareporters")){
                view.loadUrl("http://" + url);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            if (transaperentView != null && progressBar != null){
                transaperentView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (transaperentView != null && progressBar != null){
                transaperentView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (transaperentView != null && progressBar != null){
                        transaperentView.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }, 8000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }
}
