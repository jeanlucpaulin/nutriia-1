package com.example.nutriia1;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class NutritionDocumentationActivityWeb extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_documentation_activity_web);

        // WebView Initialization
        WebView webView1 = findViewById(R.id.webView1);
        WebView webView2 = findViewById(R.id.webView2);


        // Load URLs
        String nutriaURL = "https://nutriia.fr/fr/my-documentation/";

        webView1.loadUrl(nutriaURL);
        webView2.loadUrl(nutriaURL);

    }
}
