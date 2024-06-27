package com.nutriia.nutriiaemf.utils;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.activities.WebViewActivity;
import com.nutriia.nutriiaemf.adapters.DrawerItemAdapter;

public class DrawerMenu {

    private static AppCompatActivity activity;

    public static void init(AppCompatActivity activity) {
        DrawerMenu.activity = activity;

        DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
        ImageButton lateralOpenButton = activity.findViewById(R.id.lateral_open);
        ImageButton lateralCloseButton = activity.findViewById(R.id.lateral_close);
        //Button disconnectButton = activity.findViewById(R.id.disconnect_button);
        LinearLayout legalStatementsLayout = activity.findViewById(R.id.legal_statements_layout);
        LinearLayout privacyPolicyLayout = activity.findViewById(R.id.privacy_policy_layout);

        lateralOpenButton.setOnClickListener(v -> {
            //close keybord
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            drawerLayout.openDrawer(GravityCompat.START);
        });
        lateralCloseButton.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));

        Menu menu = new PopupMenu(activity, null).getMenu();
        activity.getMenuInflater().inflate(R.menu.drawer_items, menu);
        RecyclerView navRecyclerView = activity.findViewById(R.id.drawer_nav_recycler_view);
        navRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        DrawerItemAdapter navAdapter = new DrawerItemAdapter(menu, activity);
        navRecyclerView.setAdapter(navAdapter);

        legalStatementsLayout.setOnClickListener(v -> {
            Intent intent = new Intent(activity, WebViewActivity.class);
            intent.putExtra("url", "https://nutriia.fr/fr/legal-statements/");
            intent.putExtra("title", activity.getString(R.string.legal_statements));
            activity.startActivity(intent);
        });

        privacyPolicyLayout.setOnClickListener(v -> {
            Intent intent = new Intent(activity, WebViewActivity.class);
            intent.putExtra("url", "https://nutriia.fr/fr/privacy-policy-2");
            intent.putExtra("title", activity.getString(R.string.privacy_policy));
            activity.startActivity(intent);
        });

        /*disconnectButton.setOnClickListener(v -> {
            UserSharedPreferences.getInstance(activity.getApplicationContext()).clear();
            activity.startActivity(new Intent(activity, AppLaunchActivity.class));
        });*/


        TextView appVersionDrawer = activity.findViewById(R.id.app_version_drawer);

        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            String version = packageInfo.versionName;
            appVersionDrawer.setText(activity.getString(R.string.app_version, version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
