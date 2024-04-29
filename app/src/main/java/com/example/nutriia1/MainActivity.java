package com.example.nutriia1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
/*
    @Override
    protected void onPause() {
        super.onPause();
        finish(); // Fermer l'activité lorsque l'application passe en arrière-plan
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupérer les CardViews depuis le fichier XML
        CardView cardView1 = findViewById(R.id.cardView1);
        CardView cardView2 = findViewById(R.id.cardView2);
        CardView cardView3 = findViewById(R.id.cardView3);
        CardView cardView4 = findViewById(R.id.cardView4);
        CardView cardView5 = findViewById(R.id.cardView5);
        CardView cardView6 = findViewById(R.id.cardView6);
        CardView cardViewJournalier = findViewById(R.id.cardViewJournalier); // Nouvelle CardView
        CardView cardViewApportJournalier = findViewById(R.id.cardViewApportJournalier);

        // Définir les actions pour les CardViews
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AlimentActivity.class));
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NutritionDocumentationActivity.class));
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PromptActivity.class));
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IMCActivity.class));
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "CardView 5 Clicked");
                startActivity(new Intent(MainActivity.this, ConseilNutritionActivityNEW.class));
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });

        // Action pour la nouvelle CardView (Aliments Journaliers)
        cardViewJournalier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AlimentsJournaliersActivity.class));
            }
        });

        // Ajouter un OnClickListener
        cardViewApportJournalier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent pour démarrer ApportJournalierRecommandeActivity
                Intent intent = new Intent(MainActivity.this, ApportJournalierRecommandeActivity.class);
                startActivity(intent);
            }
        });


    }
}
