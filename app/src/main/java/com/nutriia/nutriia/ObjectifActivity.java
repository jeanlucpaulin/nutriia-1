package com.nutriia.nutriia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nutriia.nutriia.adapters.ButtonObjectifAdapter;
import java.util.ArrayList;
import java.util.List;

public class ObjectifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        List<ButtonObjectif> buttonDataList = new ArrayList<>();
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_salad, "Perte de\n poids"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_biceps, "Prise de\n muscles"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_lightning, "Remise en\n forme"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_sprint, "Meilleur\n endurance"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_sleep, "Qualité du\n sommeil"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_health, "Prévention\n santé"));

        RecyclerView recyclerView = findViewById(R.id.buttonList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ButtonObjectifAdapter adapter = new ButtonObjectifAdapter(buttonDataList);
        recyclerView.setAdapter(adapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        recyclerView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

        Button submitButton = (Button) findViewById(R.id.plusdinfo);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Parcourez la liste des boutons
                for (ButtonObjectif buttonData : buttonDataList) {
                    // Si le bouton est sélectionné, affichez son texte
                    if (buttonData.isSelected()) {
                        String message = "Objectif choisi : " + buttonData.getText();
                        Toast.makeText(ObjectifActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
