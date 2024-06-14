package com.nutriia.nutriia.activities;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nutriia.nutriia.models.Day;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FragmentsLayoutAdapter;
import com.nutriia.nutriia.detectors.SwipeGestureDetector;
import com.nutriia.nutriia.fragments.AppFragment;
import com.nutriia.nutriia.fragments.DishSuggestions;
import com.nutriia.nutriia.fragments.FoodComposition;
import com.nutriia.nutriia.fragments.NutrientsOfMyDay;
import com.nutriia.nutriia.fragments.MyDayAnalysis;
import com.nutriia.nutriia.fragments.MyRealDay;
import com.nutriia.nutriia.fragments.PageTitle;
import com.nutriia.nutriia.interfaces.OnValidateDay;
import com.nutriia.nutriia.interfaces.SwipeGestureCallBack;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.resources.Settings;
import com.nutriia.nutriia.utils.AccountMenu;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Override
    public void onValidateDayButtonClick(Map<String, Set<String>> userInput) {
        APISend.sendValidateDay(this, userInput, this);

        for(AppFragment fragment : fragments){
            if(fragment instanceof OnValidateDay){
                ((OnValidateDay) fragment).onValidateDayButtonClick(userInput);
            }
        }
    }

    @Override
    public void onValidateDayResponse(Day day) {
        for (AppFragment fragment : fragments) {
            if (fragment instanceof OnValidateDay) {
                ((OnValidateDay) fragment).onValidateDayResponse(day);
            }
        }
    }

    @Override
    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
        if(Settings.authorizeSwipeOnActivity()) NavBarListener.swipeActivity(this, direction);
    }
}
