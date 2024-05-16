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
        assert url != null;
        webView.loadUrl(url);

    }
}