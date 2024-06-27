package com.nutriia.nutriiaemf.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nutriia.nutriiaemf.R;

public class WebViewActivity extends AppCompatActivity {

    private class WebAppInterface {
        WebView mWebView;

        WebAppInterface(WebView webView) {
            mWebView = webView;
        }

        @JavascriptInterface
        public void domChanged() {
            mWebView.post(() -> injectJavaScript(mWebView));
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_web_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.white, getTheme()));

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        if (title != null) {
            TextView headerBackTitle = (TextView) findViewById(R.id.title);
            headerBackTitle.setText(title);
        }

        ImageButton backButton = findViewById(R.id.lateral_open);
        backButton.setOnClickListener(v -> finish());

        WebView webView = findViewById(R.id.webView);
        webView.addJavascriptInterface(new WebAppInterface(webView), "Android");
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
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
                    "setTimeout(function() {" +
                    "var elements = document.querySelectorAll('.cc-157aw.cc-1kgzy');" +
                    "elements.forEach(function(element) { element.remove(); });" +
                    "var elementById = document.getElementById('crisp-chatbox');" +
                    "if (elementById) elementById.remove();" +
                    "}, 1000);" +
                    "var observer = new MutationObserver(function(mutations) {" +
                    "Android.domChanged();" +
                    "});" +
                    "var config = { attributes: true, childList: true, characterData: true };" +
                    "observer.observe(document.body, config);" +
                    "})()";
            webView.evaluateJavascript(js, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}