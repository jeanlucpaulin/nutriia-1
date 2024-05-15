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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.DrawerItemAdapter;
import com.nutriia.nutriia.adapters.FragmentsAdapter;
import com.nutriia.nutriia.fragments.DefineMyGoal;
import com.nutriia.nutriia.fragments.DishSuggestions;
import com.nutriia.nutriia.fragments.ExampleTypicalDay;
import com.nutriia.nutriia.fragments.FoodComposition;
import com.nutriia.nutriia.fragments.MorePrecision;
import com.nutriia.nutriia.fragments.PageTitle;
import com.nutriia.nutriia.fragments.RecommendedDailyAmount;
import com.nutriia.nutriia.fragments.TipsAdvices;
import com.nutriia.nutriia.interfaces.onActivityFinishListener;
import com.nutriia.nutriia.user.UserSharedPreferences;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onActivityFinishListener {
    private RecyclerView recyclerView;

    private final List<Fragment> fragments = new ArrayList<>();

    private FragmentsAdapter adapter;

    @Override
    public void onActivityFinish() {
        setFragments(recyclerView);
    }

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

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white));

        DrawerMenu.init(this, this);

        NavBarListener.init(this, R.id.navbar_target);


        //Partie composants

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.adapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);

        this.setFragments(recyclerView);
    }

    private void setFragments(RecyclerView recyclerView) {
        fragments.clear();
        fragments.add(new PageTitle());
        fragments.add(new TipsAdvices());
        fragments.add(new MorePrecision());
        if(UserSharedPreferences.getInstance(getApplicationContext()).getGoal() == 0) fragments.add(new DefineMyGoal(this, this));
        fragments.add(new RecommendedDailyAmount());
        fragments.add(new ExampleTypicalDay());

        recyclerView.setAdapter(adapter);
    }

}