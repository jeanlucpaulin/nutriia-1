package com.nutriia.nutriia.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.adapters.DrawerItemAdapter;
import com.nutriia.nutriia.adapters.FragmentsAdapter;
import com.nutriia.nutriia.fragments.DefineMyGoal;
import com.nutriia.nutriia.fragments.DishSuggestions;
import com.nutriia.nutriia.fragments.ExampleTypicalDay;
import com.nutriia.nutriia.fragments.FoodComposition;
import com.nutriia.nutriia.fragments.MorePrecision;
import com.nutriia.nutriia.fragments.PageTitle;
import com.nutriia.nutriia.fragments.RecommendedDailyAmount;
import com.nutriia.nutriia.fragments.TipsAdvices;
import com.nutriia.nutriia.fragments.UserProfile;
import com.nutriia.nutriia.interfaces.onActivityFinishListener;
import com.nutriia.nutriia.network.APIRequest;
import com.nutriia.nutriia.user.UserSharedPreferences;
import com.nutriia.nutriia.utils.DrawerMenu;
import com.nutriia.nutriia.utils.NavBarListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements onActivityFinishListener {
    private RecyclerView recyclerView;

    private final List<Fragment> fragments = new ArrayList<>();

    private FragmentsAdapter adapter;

    @Override
    public void onActivityFinish() {
        setFragments(recyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Requete API
        new APIRequest("connect", new JSONObject(), this).send(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Handle the error
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseBody = response.body() != null ? response.body().string() : null;
                            Log.d("API", responseBody);
                        } else {
                            Log.d("API", "Request failed with status code: " + response.code() + ", message: " + response.body().string());
                        }
                    }
                }
        );


        DrawerMenu.init(this, this);

        NavBarListener.init(this, R.id.navbar_target);


        //Partie composants

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.adapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);

        this.setFragments(recyclerView);
    }

    private void setFragments(RecyclerView recyclerView) {
        fragments.clear();
        fragments.add(new PageTitle(PageTitle.ActivityType.MAIN));
        fragments.add(new TipsAdvices());
        if(UserSharedPreferences.getInstance(getApplicationContext()).isUserProfileDefined()) fragments.add(new UserProfile(this, this));
        else fragments.add(new MorePrecision(this, this));
        if(UserSharedPreferences.getInstance(getApplicationContext()).getGoal() == 0) fragments.add(new DefineMyGoal(this, this));
        fragments.add(new RecommendedDailyAmount(this));
        fragments.add(new ExampleTypicalDay());

        recyclerView.setAdapter(adapter);
    }

}