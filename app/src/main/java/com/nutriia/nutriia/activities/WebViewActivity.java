package com.nutriia.nutriia.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriia.R;

public class WebViewActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        if (title != null) {
            TextView headerBackTitle = (TextView) findViewById(R.id.title);
            headerBackTitle.setText(title);
        }

        ImageButton backButton = findViewById(R.id.lateral_open);
        backButton.setOnClickListener(v -> {
            finish();
        });

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        ((WebSettings) webSettings).setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectJavaScript(view);
            }
        });
        assert url != null;
        webView.loadUrl(url);

    }

    private void injectJavaScript(WebView webView) {
        try {
            String js = "javascript:(function() {" +
                    "var wpadminbar = document.getElementById('wpadminbar');" +
                    "if (wpadminbar) wpadminbar.style.display = 'none';" +
                    "var masthead = document.getElementById('masthead');" +
                    "if (masthead) masthead.style.display = 'none';" +
                    "var colophon = document.getElementById('colophon');" +
                    "if (colophon) colophon.style.display = 'none';" +
                    "var ktScrollUp = document.getElementById('kt-scroll-up');" +
                    "if (ktScrollUp) ktScrollUp.style.display = 'none';" +
                    "var chat = document.getElementById('mwai-chatbot-default');" +
                    "if (chat) chat.style.display = 'none';" +
                    "})()";
            webView.evaluateJavascript(js, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}