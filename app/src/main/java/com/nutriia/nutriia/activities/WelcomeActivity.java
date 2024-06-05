package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.user.UserSharedPreferences;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        Button discoverButton = findViewById(R.id.discover_app_button);

        CheckBox checkBox = findViewById(R.id.checkbox);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> UserSharedPreferences.getInstance(getApplicationContext()).setWelcomeDefined(isChecked));

        discoverButton.setOnClickListener(v -> finish());


    }
}
