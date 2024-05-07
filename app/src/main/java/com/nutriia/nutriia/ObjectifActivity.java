package com.nutriia.nutriia;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nutriia.R;

import java.util.ArrayList;
import java.util.List;

public class ObjectifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        List<ButtonObjectif> buttonDataList = new ArrayList<>();
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_salad, "Perte de\\n poids"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_biceps, "Prise de\\n muscles"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_lightning, "Remise en\\n forme"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_sprint, "Meilleur\\n endurance"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_sleep, "Qualité du\\n sommeil"));
        buttonDataList.add(new ButtonObjectif(R.drawable.menu_icon_health, "Prévention\\n santé"));

        RecyclerView recyclerView = findViewById(R.id.buttonList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new ButtonObjectifAdapter(buttonDataList));

    }
}
