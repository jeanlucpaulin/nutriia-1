package com.nutriia.nutriia.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.FormationItem;
import com.nutriia.nutriia.R;
import com.nutriia.nutriia.activities.FormationActivity;
import com.nutriia.nutriia.activities.WebViewActivity;

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
