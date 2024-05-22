package com.nutriia.nutriia.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.utils.AccountMenu;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DrawerMenu.init(this);
        NavBarListener.init(this, R.id.account);
    }
}
