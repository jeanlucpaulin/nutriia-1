package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FragmentsLayoutAdapter;
import com.nutriia.nutriia.fragments.AppFragment;
import com.nutriia.nutriia.fragments.DefineGoalButtons;
import com.nutriia.nutriia.fragments.ExampleTypicalDay;
import com.nutriia.nutriia.fragments.PageTitle;
import com.nutriia.nutriia.fragments.RecommendedDailyAmount;
import com.nutriia.nutriia.fragments.TipsAdvices;
import com.nutriia.nutriia.fragments.UserProfile;
import com.nutriia.nutriia.interfaces.OnNewGoalSelected;
import com.nutriia.nutriia.interfaces.OnUserProfileChanged;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;
import com.nutriia.nutriia.utils.AccountMenu;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnNewGoalSelected, OnUserProfileChanged {
    private LinearLayout linearLayout;

    private final List<AppFragment> fragments = new ArrayList<>();

    private FragmentsLayoutAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!UserSharedPreferences.getInstance(getApplicationContext()).isWelcomeDefined()) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DrawerMenu.init(this);
        NavBarListener.init(this, R.id.navbar_target);

        AccountMenu.init(this);


        //Partie composants

        linearLayout = findViewById(R.id.linear_layout_fragment);

        this.adapter = new FragmentsLayoutAdapter(this, linearLayout);

        this.setFragments();
    }

    private void setFragments() {
        fragments.clear();

        fragments.add(new DefineGoalButtons(this));
        fragments.add(new UserProfile(this));
        fragments.add(new RecommendedDailyAmount());
        fragments.add(new TipsAdvices());
        fragments.add(new ExampleTypicalDay());


        adapter.addAll(fragments);
    }

    @Override
    public void onNewGoalSelected(int position) {
        Log.d("DefineGoalButtons", "onNewGoalSelected: " + position);
        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(getApplicationContext());
        sharedPreferences.setGoal(position);
        sharedPreferences.clearRDA();
        sharedPreferences.clearTypicalDay();
        APISend.obtainsNewGoalRDA(this, null);
        for(AppFragment fragment : fragments) {
            if(fragment instanceof OnNewGoalSelected) {
                ((OnNewGoalSelected) fragment).onNewGoalSelected(position);
            }
        }
    }

    @Override
    public void onUserProfileChanged() {
        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(getApplicationContext());
        sharedPreferences.clearRDA();
        sharedPreferences.clearTypicalDay();
        APISend.obtainsNewGoalRDA(this, null);

        for(AppFragment fragment : fragments) {
            if(fragment instanceof OnUserProfileChanged) {
                ((OnUserProfileChanged) fragment).onUserProfileChanged();
            }
        }
    }
}