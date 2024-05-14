package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.webkit.WebView;
import android.widget.Button;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.utils.NavBarListener;

public class FormationActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);

        //   DrawerMenu.init(this);
        NavBarListener.init(this, R.id.navbar_learn);

        Button button1 = findViewById(R.id.customButton);
        Drawable drawable1 = getResources().getDrawable(R.drawable.menu_icon_plant);
        drawable1.setBounds(0, 0, 90, 90);
        button1.setCompoundDrawables(drawable1, null, null, null);
        button1.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", "https://nutriia.fr/fr/fundamentals-of-nutrition/");
            startActivity(intent);
        });

        Button button2 = findViewById(R.id.customButton2);
        Drawable drawable2 = getResources().getDrawable(R.drawable.menu_icon_macronutriment);
        drawable2.setBounds(0, 0, 90, 90);
        button2.setCompoundDrawables(drawable2, null, null, null);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", "https://nutriia.fr/fr/macronutrients/");
            startActivity(intent);
        });

        Button button3 = findViewById(R.id.customButton3);
        Drawable drawable3 = getResources().getDrawable(R.drawable.menu_icon_micronutriment);
        drawable3.setBounds(0, 0, 90, 90);
        button3.setCompoundDrawables(drawable3, null, null, null);
        button3.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", "https://nutriia.fr/fr/micronutrients/");
            startActivity(intent);
        });

        Button button4 = findViewById(R.id.customButton4);
        Drawable drawable4 = getResources().getDrawable(R.drawable.menu_icon_nutrition);
        drawable4.setBounds(0, 0, 90, 90);
        button4.setCompoundDrawables(drawable4, null, null, null);
        button4.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", "https://nutriia.fr/fr/impact-of-nutrition/");
            startActivity(intent);
        });
    }
}
