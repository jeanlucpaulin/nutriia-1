package com.example.nutriia1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TextPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_page);

        // Récupérer le TextView depuis le fichier XML
        TextView textView = findViewById(R.id.textView);

        // Ajouter le texte scrollable
        String longText = getString(R.string.long_text);  // Ajoutez votre texte ici
        textView.setText(longText);
    }
}
