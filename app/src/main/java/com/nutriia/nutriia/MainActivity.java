package com.nutriia.nutriia;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.nutriia.nutriia.adapters.DrawerItemAdapter;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton lateralOpenButton;

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

        drawerLayout = findViewById(R.id.drawer_layout);
        lateralOpenButton = findViewById(R.id.lateral_open);

        lateralOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        PopupMenu tempMenu = new PopupMenu(this, null);
        Menu menu = tempMenu.getMenu();
        getMenuInflater().inflate(R.menu.drawer_items, menu);
        RecyclerView navRecyclerView = findViewById(R.id.nav_recycler_view);
        navRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Ajoutez ces lignes pour ajouter un séparateur
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(navRecyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        navRecyclerView.addItemDecoration(dividerItemDecoration);

        DrawerItemAdapter navAdapter = new DrawerItemAdapter(menu);
        navRecyclerView.setAdapter(navAdapter);


    }
}