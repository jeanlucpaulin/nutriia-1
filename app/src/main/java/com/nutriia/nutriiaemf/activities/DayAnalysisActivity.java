package com.nutriia.nutriiaemf.activities;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nutriia.nutriiaemf.models.Day;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.adapters.FragmentsLayoutAdapter;
import com.nutriia.nutriiaemf.detectors.SwipeGestureDetector;
import com.nutriia.nutriiaemf.fragments.AppFragment;
import com.nutriia.nutriiaemf.fragments.DishSuggestions;
import com.nutriia.nutriiaemf.fragments.FoodComposition;
import com.nutriia.nutriiaemf.fragments.NutrientsOfMyDay;
import com.nutriia.nutriiaemf.fragments.MyDayAnalysis;
import com.nutriia.nutriiaemf.fragments.MyRealDay;
import com.nutriia.nutriiaemf.fragments.PageTitle;
import com.nutriia.nutriiaemf.interfaces.OnValidateDay;
import com.nutriia.nutriiaemf.interfaces.SwipeGestureCallBack;
import com.nutriia.nutriiaemf.network.APISend;
import com.nutriia.nutriiaemf.resources.Settings;
import com.nutriia.nutriiaemf.utils.AccountMenu;
import com.nutriia.nutriiaemf.utils.DrawerMenu;
import com.nutriia.nutriiaemf.utils.NavBarListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Activity for the analysis of the day
 */
public class DayAnalysisActivity extends AppCompatActivity implements OnValidateDay, SwipeGestureCallBack {

    private LinearLayout linearLayout;

    private final List<AppFragment> fragments = new ArrayList<>();

    private FragmentsLayoutAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DrawerMenu.init(this);

        NavBarListener.init(this, R.id.navbar_analysis);

        AccountMenu.init(this);

        //APISend.clear();

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

    /**
     * Set the fragments
     * Add the fragments to the list
     */
    private void setFragments() {
        fragments.clear();

        fragments.add(new MyRealDay(this));
        fragments.add(new PageTitle(PageTitle.ActivityType.ANALYSIS));
        fragments.add(new NutrientsOfMyDay(NutrientsOfMyDay.Type.ENERGY));
        fragments.add(new NutrientsOfMyDay(NutrientsOfMyDay.Type.MACRONUTRIENTS));
        fragments.add(new NutrientsOfMyDay(NutrientsOfMyDay.Type.MICRONUTRIENTS));
        fragments.add(new MyDayAnalysis());
        fragments.add(new PageTitle(PageTitle.ActivityType.HELP));
        fragments.add(new FoodComposition());
        fragments.add(new DishSuggestions());

        adapter.addAll(fragments);
    }

    /**
     * Notify the fragments that the user has clicked on the validate day button
     * @param userInput the user input
     */
    @Override
    public void onValidateDayButtonClick(Map<String, Set<String>> userInput) {
        APISend.sendValidateDay(this, userInput, this);

        for(AppFragment fragment : fragments){
            if(fragment instanceof OnValidateDay){
                ((OnValidateDay) fragment).onValidateDayButtonClick(userInput);
            }
        }
    }

    /**
     * Notify the fragments that the response from the API has been received
     * @param day the day
     */
    @Override
    public void onValidateDayResponse(Day day) {
        for (AppFragment fragment : fragments) {
            if (fragment instanceof OnValidateDay) {
                ((OnValidateDay) fragment).onValidateDayResponse(day);
            }
        }
    }

    /**
     * Notify the navbar listener that the user has swiped
     * @param direction the direction of the swipe
     */
    @Override
    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
        if(Settings.authorizeSwipeOnActivity()) NavBarListener.swipeActivity(this, direction);
    }
}
