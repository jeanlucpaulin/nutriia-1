package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.webkit.WebView;
import android.widget.Button;

import com.nutriia.nutriia.FormationItem;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.FragmentsAdapter;
import com.nutriia.nutriia.fragments.Formation;
import com.nutriia.nutriia.fragments.PageTitle;
import com.nutriia.nutriia.utils.AccountMenu;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private FragmentsAdapter adapter;

    private final List<Fragment> fragments = new ArrayList<>();

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
        NavBarListener.init(this, R.id.navbar_learn);
        AccountMenu.init(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.adapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);

        this.setFragments(recyclerView);
    }

    private void setFragments(RecyclerView recyclerView) {
        fragments.clear();

        fragments.add(new PageTitle(PageTitle.ActivityType.FORMATION));
        getItems().forEach(item -> fragments.add(new Formation(item)));

        recyclerView.setAdapter(adapter);
    }

    private List<FormationItem> getItems() {
        List<FormationItem> items = new ArrayList<>();

        List<String> urls = Arrays.asList(getResources().getStringArray(R.array.formation_urls));
        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.formation_titles));
        List<String> iconsNames = Arrays.asList(getResources().getStringArray(R.array.formation_icons));

        for(int i = 0; i < urls.size(); i++) {
            int iconId = getResources().getIdentifier(iconsNames.get(i), "drawable", getPackageName());
            items.add(new FormationItem(urls.get(i), titles.get(i), iconId));
        }

        return items;
    }
}
