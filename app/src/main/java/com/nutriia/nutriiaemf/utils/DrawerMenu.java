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

/**
 * This class is used to manage the drawer menu of the application.
 * You can define the drawer menu in the layout of the activity and then call the init method to initialize the drawer menu.
 * The init method will set the listener for each item of the drawer menu.
 */
public class DrawerMenu {

    private static AppCompatActivity activity;

    public static void init(AppCompatActivity activity) {

        /* Find elements in the layout */
        DrawerMenu.activity = activity;
        DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
        ImageButton lateralOpenButton = activity.findViewById(R.id.lateral_open);
        ImageButton lateralCloseButton = activity.findViewById(R.id.lateral_close);
        LinearLayout legalStatementsLayout = activity.findViewById(R.id.legal_statements_layout);
        LinearLayout privacyPolicyLayout = activity.findViewById(R.id.privacy_policy_layout);

        /* Set the listener for each item of the drawer menu */
        lateralOpenButton.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE); //It closed the keyboard when the drawer is opened
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            drawerLayout.openDrawer(GravityCompat.START);
        });
        lateralCloseButton.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));

        /* Set the drawer menu */
        Menu menu = new PopupMenu(activity, null).getMenu();
        activity.getMenuInflater().inflate(R.menu.drawer_items, menu);
        RecyclerView navRecyclerView = activity.findViewById(R.id.drawer_nav_recycler_view);
        navRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        DrawerItemAdapter navAdapter = new DrawerItemAdapter(menu, activity);
        navRecyclerView.setAdapter(navAdapter);

        /* Set the listener for the legal statements and privacy policy */
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

        /* Set the app version */
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
