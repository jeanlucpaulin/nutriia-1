package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.user.UserSharedPreferences;

public class AppLaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(AppLaunchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
