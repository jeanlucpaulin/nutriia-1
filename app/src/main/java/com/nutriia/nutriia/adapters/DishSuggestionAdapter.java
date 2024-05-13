package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutriia.nutriia.Dish;
import com.nutriia.nutriia.R;

import java.util.List;

public class DishSuggestionAdapter extends BaseAdapter {
    private final List<Dish> dishList;
    private final Context context;

    public DishSuggestionAdapter(Context context, List<Dish> dishList) {
        this.context = context;
        this.dishList = dishList;
    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    @Override
    public Dish getItem(int position) {
        return dishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_component_dish_suggestions, parent, false);
        }

        TextView dishName = convertView.findViewById(R.id.item_content);

        Dish dish = getItem(position);

        dishName.setText(dish.getName());

        return convertView;
    }
}
