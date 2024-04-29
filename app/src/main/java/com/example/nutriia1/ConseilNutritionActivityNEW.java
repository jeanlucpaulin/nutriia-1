package com.example.nutriia1;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ConseilNutritionActivityNEW extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conseil_nutrition_new);

        Log.d("ConseilNutritionActivityNEW", "Activity Created");

        // Récupérer la WebView depuis le fichier XML
        WebView webView = findViewById(R.id.webView);

        // Activer JavaScript dans la WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Configurer le client WebView pour ouvrir les liens dans la même WebView
        webView.setWebViewClient(new WebViewClient());

        // Charger l'URL du site Web dans la WebView
        webView.loadUrl("https://www.nutriia.fr//fr//my-member-space-inapp//");
    }
}
