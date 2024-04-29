package com.example.nutriia1;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class MonProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profil);

        // Initialize WebView
        WebView webView = findViewById(R.id.webView);

        // Load a web page (replace "your_website_url" with the actual URL you want to load)
        webView.loadUrl("https://nutriia.fr//member-space//");
    }
}