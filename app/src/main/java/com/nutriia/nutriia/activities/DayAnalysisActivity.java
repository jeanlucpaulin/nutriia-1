package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriia.Day;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.DrawerItemAdapter;
import com.nutriia.nutriia.adapters.FragmentsAdapter;
import com.nutriia.nutriia.fragments.DefineMyGoal;
import com.nutriia.nutriia.fragments.DishSuggestions;
import com.nutriia.nutriia.fragments.ExampleTypicalDay;
import com.nutriia.nutriia.fragments.FoodComposition;
import com.nutriia.nutriia.fragments.MacronutrientsOfMyDay;
import com.nutriia.nutriia.fragments.MicronutrientsOfMyDay;
import com.nutriia.nutriia.fragments.MorePrecision;
import com.nutriia.nutriia.fragments.MyDayAnalysis;
import com.nutriia.nutriia.fragments.MyRealDay;
import com.nutriia.nutriia.fragments.PageTitle;
import com.nutriia.nutriia.fragments.RecommendedDailyAmount;
import com.nutriia.nutriia.fragments.TipsAdvices;
import com.nutriia.nutriia.interfaces.OnValidateDay;
import com.nutriia.nutriia.network.APISend;
import com.nutriia.nutriia.user.UserSharedPreferences;
import com.nutriia.nutriia.utils.AccountMenu;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DayAnalysisActivity extends AppCompatActivity implements OnValidateDay {

    private RecyclerView recyclerView;

    private final List<Fragment> fragments = new ArrayList<>();

    private FragmentsAdapter adapter;


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

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.adapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);

        this.setFragments(recyclerView);
    }

    private void setFragments(RecyclerView recyclerView) {
        fragments.clear();


        fragments.add(new MyRealDay(this));
        fragments.add(new MacronutrientsOfMyDay());
        fragments.add(new MicronutrientsOfMyDay());
        fragments.add(new FoodComposition());
        fragments.add(new DishSuggestions());
        fragments.add(new MyDayAnalysis());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onValidateDayButtonClick(Map<String, Set<String>> userInput) {
        APISend.sendValidateDay(this, userInput, this);

        for(Fragment fragment : fragments){
            if(fragment instanceof OnValidateDay){
                ((OnValidateDay) fragment).onValidateDayButtonClick(userInput);
            }
        }
    }

    @Override
    public void onValidateDayResponse(Day day) {
        for (Fragment fragment : fragments) {
            if (fragment instanceof OnValidateDay) {
                ((OnValidateDay) fragment).onValidateDayResponse(day);
            }
        }
    }
}
