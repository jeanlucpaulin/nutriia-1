package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nutriia.nutriia.Nutrient;
import com.nutriia.nutriia.R;

import java.util.List;

public class DayProgressionAdapter extends BaseAdapter {
    private Context context;

    private List<Nutrient> nutrientsList;

    public DayProgressionAdapter(Context context, List<Nutrient> nutrientsList) {
        this.context = context;
        this.nutrientsList = nutrientsList;
    }

    @Override
    public int getCount() {
        return nutrientsList.size();
    }

    @Override
    public Object getItem(int position) {
        return nutrientsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Nutrient nutrients = nutrientsList.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_my_day, null);
        }

        TextView name = convertView.findViewById(R.id.item_name);
        TextView value = convertView.findViewById(R.id.item_value);
        ProgressBar progressBar = convertView.findViewById(R.id.progressBar);

        name.setText(nutrients.getName());
        value.setText(String.valueOf(nutrients.getValue() + nutrients.getUnit()));
        progressBar.setProgress(nutrients.getProgress());

        return convertView;
    }


}
