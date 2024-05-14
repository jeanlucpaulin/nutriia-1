package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriia.user.UserSharedPreferences;

public class AppLaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (UserSharedPreferences.getInstance(getApplicationContext()).getGoal() == -1) {
            startActivity(new Intent(this, ObjectifActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

    }
}
