package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.SpacesItemDecoration;
import com.nutriia.nutriia.adapters.ButtonObjectifAdapter;
import com.nutriia.nutriia.builders.GoalsBuilder;
import com.nutriia.nutriia.interfaces.OnClickOnGoal;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectifActivity extends AppCompatActivity implements OnClickOnGoal {

    private final List<Goal> goals = new ArrayList<>();

    private RecyclerView recyclerView;

    private ButtonObjectifAdapter adapter;

    private Button submitButton;

    private TextView goalDescription;

    @Override
    public void onClickOnGoal(boolean isSelected) {
        submitButton.setEnabled(isSelected);
        submitButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        if(isSelected) {
            for (Goal buttonGoal : goals) {
                if (buttonGoal.isSelected()) goalDescription.setText(buttonGoal.getDescription());
            }
        }
        else {
            goalDescription.setText(getResources().getString(R.string.no_goal_selected));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        recyclerView = findViewById(R.id.buttonList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        goalDescription = findViewById(R.id.coachResponse);
        goalDescription.setText(getResources().getString(R.string.no_goal_selected));

        goals.clear();

        GoalsBuilder goalsBuilder = new GoalsBuilder(getResources(), getPackageName(), UserSharedPreferences.getInstance(getApplicationContext()));

        goals.addAll(goalsBuilder.getGoals(getResources(), getPackageName(), true));

        adapter = new ButtonObjectifAdapter(this.goals, this);

        recyclerView.setAdapter(adapter);


        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        recyclerView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

        submitButton = (Button) findViewById(R.id.plusdinfo);

        submitButton.setEnabled(false);
        submitButton.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));

        Button tryWithoutGoal = (Button) findViewById(R.id.decrouvrirSansObjectif);
        tryWithoutGoal.setOnClickListener(v -> {
            UserSharedPreferences.getInstance(getApplicationContext()).setGoal(0);
            startActivity(new Intent(this, MainActivity.class));
        });

        submitButton.setOnClickListener(v -> {
            // Parcourez la liste des boutons
            int goal_id = 1;
            for (Goal buttonData : this.goals) {
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
