package com.ayetstudios.ayetandroidsdkdemoapp;

import android.os.Bundle;
/*
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ayetstudios.publishersdk.AyetSdk;


public class WebviewActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.);
        setSupportActionBar(toolbar);*/

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mWebView = (WebView) findViewById(R.id.mywebview);
        if (savedInstanceState == null) {
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.clearHistory();
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebChromeClient(new WebChromeClient());
            try {
                mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
            } catch (Exception e) {
                Log.d("mWebView", "setMediaPlaybackRequiresUserGesture failed");

            }
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.loadUrl("https://www.ayetstudios.com/offers/web_offerwall/536?external_identifier=user_identifier_001");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mWebView.restoreState(savedInstanceState);
    }

}
