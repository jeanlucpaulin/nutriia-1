package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutriia.nutriia.R;
import com.nutriia.nutriia.interfaces.IItemRDA;

import java.util.List;

public class ItemRDAAdapter extends BaseAdapter {

    private Context context;
    private List<IItemRDA> items;

    public ItemRDAAdapter(Context context, List<IItemRDA> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public IItemRDA getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_recommanded_daily_amount, parent, false);
        }

        IItemRDA item = getItem(position);

        TextView itemName = convertView.findViewById(R.id.name);
        itemName.setText(item.getName());

        TextView itemAmount = convertView.findViewById(R.id.amount);
        itemAmount.setText(String.valueOf(item.getAmount()));

        TextView itemUnit = convertView.findViewById(R.id.unit);
        itemUnit.setText(item.getUnit());

        return convertView;
    }
}
