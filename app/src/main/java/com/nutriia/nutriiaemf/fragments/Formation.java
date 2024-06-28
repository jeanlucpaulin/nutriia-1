package com.nutriia.nutriiaemf.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nutriia.nutriiaemf.models.FormationItem;
import com.nutriia.nutriiaemf.R;
import com.nutriia.nutriiaemf.activities.WebViewActivity;

/**
 * Formation class
 * This class is used to create a formation fragment linked to the website

 */
public class Formation extends AppFragment {

    private final FormationItem formationItem;

    private Context context;

    public Formation(FormationItem formationItem) {
        this.formationItem = formationItem;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public void create(FrameLayout frameLayout) {
        context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.component_formation, frameLayout, false);

        ImageView imageView = view.findViewById(R.id.icon);
        TextView textView = view.findViewById(R.id.formation);

        imageView.setImageResource(formationItem.getIcon());
        textView.setText(formationItem.getTitle());

        view.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url", formationItem.getUrl());
            intent.putExtra("title", formationItem.getTitle());
            context.startActivity(intent);
        });

        frameLayout.addView(view);
    }
}
