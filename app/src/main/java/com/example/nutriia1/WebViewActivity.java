package com.example.nutriia1;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // Récupérez l'URL à partir de l'intent
        String url = getIntent().getStringExtra("url");

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        // Chargez l'URL dans le WebView
        if (url != null && !url.isEmpty()) {
            webView.loadUrl(url);
        }

        /*
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://nutriia.fr//fr//fundamentals-of-nutrition-inapp//");*/

    }
}
