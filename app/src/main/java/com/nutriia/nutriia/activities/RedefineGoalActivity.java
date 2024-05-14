package com.nutriia.nutriia.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FragmentsAdapter;
import com.nutriia.nutriia.fragments.RedefineMyGoal;

import java.util.ArrayList;
import java.util.List;

public class RedefineGoalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefine_goal);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RedefineMyGoal(new Goal("Prise de muscles", "menu_icon_biceps", true)));
        fragments.add(new RedefineMyGoal(new Goal("Perte de poids", "menu_icon_salad")));
        fragments.add(new RedefineMyGoal(new Goal("Remise en forme", "menu_icon_lightning")));
        fragments.add(new RedefineMyGoal(new Goal("Meilleur endurance", "menu_icon_sprint")));
        fragments.add(new RedefineMyGoal(new Goal("Qualité du sommeil", "menu_icon_sleep")));
        fragments.add(new RedefineMyGoal(new Goal("Prévention santé", "menu_icon_health")));

        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);
        recyclerView.setAdapter(adapter);
    }
}
