package com.nutriia.nutriia.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.utils.AccountMenu;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

public class CoachActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meet);  // Assurez-vous que le layout XML est correctement nommé

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NavBarListener.init(this, R.id.navbar_coach);
        DrawerMenu.init(this);
        AccountMenu.init(this);

        //APISend.clear();

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectJavaScript();
            }
        });
        webView.loadUrl("https://nutriia.fr/en_us/mobile-chatbot/");
    }

    private void injectJavaScript() {
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
