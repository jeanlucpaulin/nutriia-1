package com.example.nutriia1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PromptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        CardView cardView1 = findViewById(R.id.cardView1);
        TextView cardView1Text = findViewById(R.id.cardView1Text);
        cardView1Text.setText(getString(R.string.prompt_gagnerenforce));

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_gagnerenforce); // Obtenez le texte de la ressource

                // Créez un Intent pour passer le texte à ChatActivityPrompt
                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                // Lancez l'activité ChatActivityPrompt avec l'Intent
                startActivity(intent);
            }
        });


        // Répétez le même modèle pour les autres CardViews
        CardView cardView2 = findViewById(R.id.cardView2);
        TextView cardView2Text = findViewById(R.id.cardView2Text);
        cardView2Text.setText(getString(R.string.prompt_gagnerenendurance));

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_gagnerenendurance);

                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                startActivity(intent);
            }
        });

        CardView cardView3 = findViewById(R.id.cardView3);
        TextView cardView3Text = findViewById(R.id.cardView3Text);
        cardView3Text.setText(getString(R.string.prompt_recupererendurance));

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_recupererendurance); // Obtenez le texte de la ressource

                // Créez un Intent pour passer le texte à ChatActivityPrompt
                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                // Lancez l'activité ChatActivityPrompt avec l'Intent
                startActivity(intent);
            }
        });

        // Répétez le même modèle pour les autres CardViews
        CardView cardView4 = findViewById(R.id.cardView4);
        TextView cardView4Text = findViewById(R.id.cardView4Text);
        cardView4Text.setText(getString(R.string.prompt_recupererforce));

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_recupererforce);

                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                startActivity(intent);
            }
        });

        CardView cardView5 = findViewById(R.id.cardView5);
        TextView cardView5Text = findViewById(R.id.cardView5Text);
        cardView5Text.setText(getString(R.string.prompt_perdredupoids));

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_perdredupoids); // Obtenez le texte de la ressource

                // Créez un Intent pour passer le texte à ChatActivityPrompt
                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                // Lancez l'activité ChatActivityPrompt avec l'Intent
                startActivity(intent);
            }
        });

        // Répétez le même modèle pour les autres CardViews
        CardView cardView6 = findViewById(R.id.cardView6);
        TextView cardView6Text = findViewById(R.id.cardView6Text);
        cardView6Text.setText(getString(R.string.prompt_etreenforme));

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_etreenforme);

                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                startActivity(intent);
            }
        });

        CardView cardView7 = findViewById(R.id.cardView7);
        TextView cardView7Text = findViewById(R.id.cardView7Text);
        cardView7Text.setText(getString(R.string.prompt_avoirenergie));

        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_avoirenergie); // Obtenez le texte de la ressource

                // Créez un Intent pour passer le texte à ChatActivityPrompt
                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                // Lancez l'activité ChatActivityPrompt avec l'Intent
                startActivity(intent);
            }
        });

        // Répétez le même modèle pour les autres CardViews
        CardView cardView8 = findViewById(R.id.cardView8);
        TextView cardView8Text = findViewById(R.id.cardView8Text);
        cardView8Text.setText(getString(R.string.prompt_avoirmoral));

        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_avoirmoral);

                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                startActivity(intent);
            }
        });

        CardView cardView9 = findViewById(R.id.cardView9);
        TextView cardView9Text = findViewById(R.id.cardView9Text);
        cardView9Text.setText(getString(R.string.prompt_sante));

        cardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_sante); // Obtenez le texte de la ressource

                // Créez un Intent pour passer le texte à ChatActivityPrompt
                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                // Lancez l'activité ChatActivityPrompt avec l'Intent
                startActivity(intent);
            }
        });

        // Répétez le même modèle pour les autres CardViews
        CardView cardView10 = findViewById(R.id.cardView10);
        TextView cardView10Text = findViewById(R.id.cardView10Text);
        cardView10Text.setText(getString(R.string.prompt_cardio));

        cardView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_cardio);

                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                startActivity(intent);
            }
        });

        CardView cardView11 = findViewById(R.id.cardView11);
        TextView cardView11Text = findViewById(R.id.cardView11Text);
        cardView11Text.setText(getString(R.string.prompt_metaboliques));

        cardView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_metaboliques); // Obtenez le texte de la ressource

                // Créez un Intent pour passer le texte à ChatActivityPrompt
                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                // Lancez l'activité ChatActivityPrompt avec l'Intent
                startActivity(intent);
            }
        });

        CardView cardView12 = findViewById(R.id.cardView12);
        TextView cardView12Text = findViewById(R.id.cardView12Text);
        cardView12Text.setText(getString(R.string.prompt_musculo));

        cardView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getString(R.string.prompt_musculo); // Obtenez le texte de la ressource

                // Créez un Intent pour passer le texte à ChatActivityPrompt
                Intent intent = new Intent(PromptActivity.this, ChatActivityPrompt.class);
                intent.putExtra("cardText", text);

                // Lancez l'activité ChatActivityPrompt avec l'Intent
                startActivity(intent);
            }
        });



        // Ajoutez d'autres CardViews en suivant le même modèle
    }
}
