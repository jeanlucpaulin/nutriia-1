package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.SpacesItemDecoration;
import com.nutriia.nutriia.adapters.ButtonObjectifAdapter;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        List<String> icons = Arrays.asList(getResources().getStringArray(R.array.icons_goals));
        List<String> goals = Arrays.asList(getResources().getStringArray(R.array.goals));

        List<Goal> buttonDataList = new ArrayList<>();

        for(int i = 1; i < icons.size(); i++) {
            buttonDataList.add(new Goal(getResources().getIdentifier(icons.get(i), "drawable", getPackageName()), goals.get(i)));
        }

        RecyclerView recyclerView = findViewById(R.id.buttonList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ButtonObjectifAdapter adapter = new ButtonObjectifAdapter(buttonDataList);
        recyclerView.setAdapter(adapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        recyclerView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

        Button submitButton = (Button) findViewById(R.id.plusdinfo);

        Button tryWithoutGoal = (Button) findViewById(R.id.decrouvrirSansObjectif);
        tryWithoutGoal.setOnClickListener(v -> {
            UserSharedPreferences.getInstance(getApplicationContext()).setGoal(0);
            startActivity(new Intent(this, MainActivity.class));
        });

        submitButton.setOnClickListener(v -> {
            // Parcourez la liste des boutons
            int goal_id = 1;
            for (Goal buttonData : buttonDataList) {
                // Si le bouton est sélectionné, affichez son texte
                if (buttonData.isSelected()) {
                    UserSharedPreferences.getInstance(getApplicationContext()).setGoal(goal_id);
                    startActivity(new Intent(this, MainActivity.class));
                }
                goal_id++;
            }

        });
    }
}
