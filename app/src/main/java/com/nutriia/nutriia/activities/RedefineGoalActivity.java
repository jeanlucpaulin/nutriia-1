package com.nutriia.nutriia.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Goal;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FragmentsAdapter;
import com.nutriia.nutriia.builders.GoalsBuilder;
import com.nutriia.nutriia.fragments.DefineMyGoal;
import com.nutriia.nutriia.fragments.RedefineMyGoal;
import com.nutriia.nutriia.interfaces.OnClickOnGoal;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedefineGoalActivity extends AppCompatActivity implements OnClickOnGoal {
    private RecyclerView recyclerView;

    private Button validateButton;

    @Override
    public void onClickOnGoal(boolean isSelected) {
        validateButton.setEnabled(isSelected);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_redefine_goal);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_redefine_goal), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.white, getTheme()));

        ImageButton backButton = findViewById(R.id.lateral_open);
        backButton.setOnClickListener(v -> {
            finish();
        });

        validateButton = findViewById(R.id.button_validate);

        TextView title = findViewById(R.id.title);
        boolean isAlreadyDefined = UserSharedPreferences.getInstance(getApplicationContext()).isUserGoalDefined();
        title.setText(isAlreadyDefined ? R.string.redefine_goal: R.string.define_goal);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Fragment> fragments = new ArrayList<>();

        GoalsBuilder goalsBuilder = new GoalsBuilder(getResources(), getPackageName(), UserSharedPreferences.getInstance(getApplicationContext()));

        List<Goal> goalsList = goalsBuilder.getGoals(getResources(), getPackageName());

        goalsList.forEach(goal -> {
            fragments.add(new RedefineMyGoal(goal));
        });

        //FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), fragments, true, this);
        //recyclerView.setAdapter(adapter);


        validateButton.setEnabled(false);

        validateButton.setOnClickListener(click -> {
            for (int i = 0; i < fragments.size(); i++) {
                if (((RedefineMyGoal) fragments.get(i)).getGoal().isSelected()) {
                    UserSharedPreferences userSharedPreferences = UserSharedPreferences.getInstance(getApplicationContext());
                    userSharedPreferences.setGoal(i);
                    userSharedPreferences.clearRDA();
                    userSharedPreferences.clearTypicalDay();
                    APISend.obtainsNewGoalRDA(this, null);

                    finish();
                    return;
                }
            }
        });

    }
}
