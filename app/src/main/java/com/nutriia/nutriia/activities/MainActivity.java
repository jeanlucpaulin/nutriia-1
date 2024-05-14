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
import com.nutriia.nutriia.user.UserSharedPreferences;
import com.nutriia.nutriia.utils.NavBarListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private DrawerLayout drawerLayout;
    private ImageButton lateralOpenButton;
    private ImageButton lateralCloseButton;
    private Button disconnectButton;
    private TextView appVersionDrawer;

    private RecyclerView recyclerView;

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

        drawerLayout = findViewById(R.id.drawer_layout);
        lateralOpenButton = findViewById(R.id.lateral_open);
        lateralCloseButton = findViewById(R.id.lateral_close);
        disconnectButton = findViewById(R.id.disconnect_button);


        this.setDrawerListeners();

        Menu menu = new PopupMenu(this, null).getMenu();
        getMenuInflater().inflate(R.menu.drawer_items, menu);
        RecyclerView navRecyclerView = findViewById(R.id.drawer_nav_recycler_view);
        navRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DrawerItemAdapter navAdapter = new DrawerItemAdapter(menu);
        navRecyclerView.setAdapter(navAdapter);

        disconnectButton.setOnClickListener(v -> {
            UserSharedPreferences.getInstance(getApplicationContext()).clear();
            startActivity(new Intent(this, AppLaunchActivity.class));
        });

        appVersionDrawer = findViewById(R.id.app_version_drawer);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = packageInfo.versionName;
            appVersionDrawer.setText(getString(R.string.app_version, version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        NavBarListener.init(this, R.id.navbar_target);


        //Partie composants

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PageTitle());
        fragments.add(new TipsAdvices());
        fragments.add(new MorePrecision());
        if(UserSharedPreferences.getInstance(getApplicationContext()).getGoal() == 0) fragments.add(new DefineMyGoal());
        fragments.add(new RecommendedDailyAmount());
        fragments.add(new ExampleTypicalDay());
        fragments.add(new FoodComposition());
        fragments.add(new DishSuggestions());

        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);
        recyclerView.setAdapter(adapter);



    }

    private void setDrawerListeners(){
        lateralOpenButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        lateralCloseButton.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));
    }

}