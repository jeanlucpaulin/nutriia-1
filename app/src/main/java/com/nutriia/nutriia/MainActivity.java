package com.nutriia.nutriia;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import com.google.android.material.navigation.NavigationView;
import com.nutriia.nutriia.adapters.DrawerItemAdapter;
import com.nutriia.nutriia.utils.NavBarListener;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton lateralOpenButton;
    private ImageButton lateralCloseButton;
    private Button disconnectButton;
    private TextView appVersionDrawer;

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
            Log.d("Disconnect", "Disconnect button clicked");
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


    }

    private void setDrawerListeners(){
        lateralOpenButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        lateralCloseButton.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));
    }

}