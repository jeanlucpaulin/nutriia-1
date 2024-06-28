package com.nutriia.nutriiaemf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.adapters.FragmentsLayoutAdapter;
import com.nutriia.nutriiaemf.detectors.SwipeGestureDetector;
import com.nutriia.nutriiaemf.fragments.AppFragment;
import com.nutriia.nutriiaemf.fragments.DefineGoalButtons;
import com.nutriia.nutriiaemf.fragments.ExampleTypicalDay;
import com.nutriia.nutriiaemf.fragments.RecommendedDailyAmount;
import com.nutriia.nutriiaemf.fragments.TipsAdvices;
import com.nutriia.nutriiaemf.fragments.UserProfile;
import com.nutriia.nutriiaemf.interfaces.OnNewGoalSelected;
import com.nutriia.nutriiaemf.interfaces.OnUserProfileChanged;
import com.nutriia.nutriiaemf.interfaces.SwipeGestureCallBack;
import com.nutriia.nutriiaemf.network.APISend;
import com.nutriia.nutriiaemf.resources.Settings;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;
import com.nutriia.nutriiaemf.utils.AccountMenu;
import com.nutriia.nutriiaemf.utils.DrawerMenu;
import com.nutriia.nutriiaemf.utils.NavBarListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnNewGoalSelected, OnUserProfileChanged, SwipeGestureCallBack {
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

        linearLayout.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));

        linearLayout.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));


        this.adapter = new FragmentsLayoutAdapter(this, linearLayout);

        this.setFragments();
    }

    private void setFragments() {
        fragments.clear();
    //    fragments.add(new PageTitle(PageTitle.ActivityType.MAIN));
        fragments.add(new DefineGoalButtons(this));
        fragments.add(new UserProfile(this));
        fragments.add(new RecommendedDailyAmount());
        fragments.add(new TipsAdvices());
        fragments.add(new ExampleTypicalDay());


        adapter.addAll(fragments);
    }

    /**
     * Set the new goal
     * Save the new goal in the shared preferences
     * Clear the RDA and the typical day
     * Call the API to get the new RDA
     * Notify the fragments that the goal has changed
     * @param position the position of the new goal
     */
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

    /**
     * Notify the fragments that the user profile has changed
     */
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

    /**
     * Notify the navBarListener that the user has swiped
     * @param direction the direction of the swipe
     */
    @Override
    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
        if(Settings.authorizeSwipeOnActivity()) NavBarListener.swipeActivity(this, direction);
    }
}