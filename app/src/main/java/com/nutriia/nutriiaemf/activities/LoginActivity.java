package com.nutriia.nutriiaemf.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.utils.DrawerMenu;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DrawerMenu.init(this);
        //NavBarListener.init(this, R.id.account);
    }
}
