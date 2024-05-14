package com.nutriia.nutriia.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.utils.NavBarListener;

public class WebViewActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //   DrawerMenu.init(this);
        NavBarListener.init(this, R.id.navbar_learn);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        ((WebSettings) webSettings).setJavaScriptEnabled(true);
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);

    }
}