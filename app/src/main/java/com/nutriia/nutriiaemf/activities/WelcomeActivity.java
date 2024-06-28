package com.nutriia.nutriiaemf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.user.UserSharedPreferences;

/**
 * Activity for welcoming the user
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        getWindow().setNavigationBarColor(getColor(R.color.green_nutriia));

        Button discoverButton = findViewById(R.id.discover_app_button);

        CheckBox checkBox = findViewById(R.id.checkbox);

        changeButtonState(discoverButton, checkBox.isChecked());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserSharedPreferences.getInstance(getApplicationContext()).setWelcomeDefined(isChecked);

            changeButtonState(discoverButton, isChecked);

        });

        discoverButton.setOnClickListener(v -> finish());

        LinearLayout layout = findViewById(R.id.show_eula_layout);

        layout.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://nutriia.fr/fr/eula-inapp/");
            intent.putExtra("title", this.getString(R.string.eula_title));
            this.startActivity(intent);
        });

    }

    private void changeButtonState(Button button, boolean state) {
        button.setEnabled(state);
        button.setAlpha(state ? 1f : 0.5f);
    }

}
