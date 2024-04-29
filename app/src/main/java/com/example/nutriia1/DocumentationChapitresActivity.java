package com.example.nutriia1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DocumentationChapitresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation_chapitres);

        // Récupérer les CardViews depuis le fichier XML
        CardView cardView1 = findViewById(R.id.cardView1);
        CardView cardView2 = findViewById(R.id.cardView2);
        CardView cardView3 = findViewById(R.id.cardView3);
        CardView cardView4 = findViewById(R.id.cardView4);

        // Définir les actions pour les CardViews
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Action pour CardView 1 (Remplacez par l'action souhaitée)
                startActivity(new Intent(DocumentationChapitresActivity.this, Chapitre1Activity.class));
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Action pour CardView 2 (Remplacez par l'action souhaitée)
                startActivity(new Intent(DocumentationChapitresActivity.this, Chapitre2Activity.class));
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Action pour CardView 3 (Remplacez par l'action souhaitée)
                startActivity(new Intent(DocumentationChapitresActivity.this, Chapitre3Activity.class));
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Action pour CardView 4 (Remplacez par l'action souhaitée)
                startActivity(new Intent(DocumentationChapitresActivity.this, Chapitre4Activity.class));
            }
        });
    }
}
