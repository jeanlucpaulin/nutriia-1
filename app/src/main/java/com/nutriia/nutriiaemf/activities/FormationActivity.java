package com.nutriia.nutriiaemf.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.GestureDetector;
import android.widget.LinearLayout;

import com.nutriia.nutriiaemf.models.FormationItem;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.adapters.FragmentsLayoutAdapter;
import com.nutriia.nutriiaemf.detectors.SwipeGestureDetector;
import com.nutriia.nutriiaemf.fragments.AppFragment;
import com.nutriia.nutriiaemf.fragments.Formation;
import com.nutriia.nutriiaemf.fragments.FormationBanner;
import com.nutriia.nutriiaemf.fragments.PageTitle;
import com.nutriia.nutriiaemf.interfaces.SwipeGestureCallBack;
import com.nutriia.nutriiaemf.resources.Settings;
import com.nutriia.nutriiaemf.utils.AccountMenu;
import com.nutriia.nutriiaemf.utils.DrawerMenu;
import com.nutriia.nutriiaemf.utils.NavBarListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormationActivity extends AppCompatActivity implements SwipeGestureCallBack {

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
        NavBarListener.init(this, R.id.navbar_learn);
        AccountMenu.init(this);

        //APISend.clear();

        linearLayout = findViewById(R.id.linear_layout_fragment);

        GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));

        linearLayout.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));


        this.adapter = new FragmentsLayoutAdapter(this, linearLayout);

        this.setFragments();
    }

    private void setFragments() {
        fragments.clear();
        fragments.add(new FormationBanner());
        fragments.add(new PageTitle(PageTitle.ActivityType.FORMATION));
        for(int i = 0; i < getItems().size(); i++) {
            fragments.add(new Formation(getItems().get(i)));
        }


        adapter.addAll(fragments);
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

    /**
     * Method called when a swipe is detected
     * @param direction the direction of the swipe
     */
    @Override
    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
        if(Settings.authorizeSwipeOnActivity()) NavBarListener.swipeActivity(this, direction);
    }
}
