package com.example.nutriia1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NutritionDocumentationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_documentation_activity);

        // Récupérez le bouton depuis le fichier XML
        Button openDocumentationChapitresButton = findViewById(R.id.openDocumentationChapitresButton);

        // Définissez un écouteur de clic sur le bouton
        openDocumentationChapitresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ouvrez l'activité DocumentationChapitres lorsque le bouton est cliqué
                startActivity(new Intent(NutritionDocumentationActivity.this, DocumentationChapitresActivity.class));
            }
        });
    }
}
