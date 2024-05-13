package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.nutriia.nutriia.Meal;
import com.nutriia.nutriia.R;

import java.util.List;

public class MealsAdapter extends BaseAdapter {
    private Context context;
    private List<Meal> meals;

    private boolean allowEdit = false;

    public MealsAdapter(Context context, List<Meal> meals) {
        this(context, meals, false);
    }

    public MealsAdapter(Context context, List<Meal> meals, boolean allowEdit) {
        this.context = context;
        this.meals = meals;
        this.allowEdit = allowEdit;
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @Override
    public Meal getItem(int position) {
        return meals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.component_meal, parent, false);
        }

        Meal meal = getItem(position);

        TextView mealName = convertView.findViewById(R.id.component_title);
        mealName.setText(meal.getName());

        EditText mealContent = convertView.findViewById(R.id.meal_content);
        mealContent.setActivated(allowEdit);
        mealContent.setFocusable(allowEdit);

        String mealContentString = getMealContent(position);

        mealContent.setText(mealContentString);

        return convertView;
    }

    public String getMealContent(int position) {
        Meal meal = getItem(position);
        int maxLength = 0;
        for(Meal m : meals) {
            if(m.getContent().length() > maxLength) {
                maxLength = m.getContent().length();
            }
        }

        Log.d("MealsAdapter", "maxLength: " + maxLength);

        String mealContent = meal.getContent();
        while(mealContent.length() < maxLength) {
            mealContent += " ";
        }
        mealContent += " ";

        Log.d("MealsAdapter", "mealContent: " + mealContent.length());

        return mealContent;
    }
}
