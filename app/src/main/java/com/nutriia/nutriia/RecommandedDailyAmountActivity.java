package com.nutriia.nutriia;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.nutriia.nutriia.adapters.ItemRDAAdapter;
import com.nutriia.nutriia.interfaces.IItemRDA;

import java.util.List;

public class RecommandedDailyAmountActivity extends AppCompatActivity {
    private List<IItemRDA> macronutrients;
    private List<IItemRDA> micronutrients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_recommanded_daily_amount);


        //TODO : DELETE THIS
        macronutrients = List.of(
            new ItemRDA("Proteins", "g", 50),
            new ItemRDA("Carbohydrates", "g", 300),
            new ItemRDA("Lipids", "g", 70)
        );

        micronutrients = List.of(
            new ItemRDA("Vitamin A", "µg", 800),
            new ItemRDA("Vitamin C", "mg", 110),
            new ItemRDA("Vitamin D", "µg", 5)
        );

        ListView macronutrientsListView = findViewById(R.id.macronutrients_list);
        ListView micronutrientsListView = findViewById(R.id.micronutrients_list);

        ItemRDAAdapter macronutrientsAdapter = new ItemRDAAdapter(this, macronutrients);
        ItemRDAAdapter micronutrientsAdapter = new ItemRDAAdapter(this, micronutrients);

        macronutrientsListView.setAdapter(macronutrientsAdapter);
        micronutrientsListView.setAdapter(micronutrientsAdapter);

    }
}
