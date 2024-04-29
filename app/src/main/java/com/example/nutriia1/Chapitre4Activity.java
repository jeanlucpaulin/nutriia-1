package com.example.nutriia1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Chapitre4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapitre4);

        Button openWebViewButton = findViewById(R.id.openWebViewButton);
        openWebViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Créez une intention pour démarrer l'activité WebView
                Intent intent = new Intent(Chapitre4Activity.this, WebViewActivity.class);
                // Ajoutez l'URL du site Web en tant que données supplémentaires
                intent.putExtra("url", "https://nutriia.fr//fr//impact-of-nutrition-inapp//");
                // Démarrez l'activité WebView
                startActivity(intent);
            }
        });

    }
}
