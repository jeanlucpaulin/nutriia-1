package com.nutriia.nutriia;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriia.adapters.DayProgressionAdapter;

import java.util.ArrayList;
import java.util.List;

public class NutrientsOfMyDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_my_day);

        Resources resources = getResources();

        ListView listView = findViewById(R.id.listView);
        TextView textView = findViewById(R.id.component_title);
        textView.setText(R.string.macronutrients_of_my_day);


        List<Nutrients> nutrientsList = new ArrayList<>();
        for(String macronutrient : resources.getStringArray(R.array.list_macronutrients)) {
            nutrientsList.add(new Nutrients(macronutrient, 100, resources.getString(R.string.grams_unit), 50));
        }


        DayProgressionAdapter dayProgressionAdapter = new DayProgressionAdapter(this, nutrientsList);
        listView.setAdapter(dayProgressionAdapter);
    }
}
