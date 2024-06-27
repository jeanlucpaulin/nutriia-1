package com.nutriia.nutriiaemf.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriiaemf.R;

public class AccountMenu {

    private static AppCompatActivity activity;

    public static void init(AppCompatActivity activity){
        AccountMenu.activity = activity;

        /*ImageButton accountImageButton = activity.findViewById(R.id.account);

        accountImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });*/
    }
}