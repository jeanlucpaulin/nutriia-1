package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.nutriia.nutriia.fragments.AppFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentsLayoutAdapter {
    private List<AppFragment> fragments;

    private Context context;

    private LinearLayout linearLayout;

    public FragmentsLayoutAdapter(Context context, LinearLayout linearLayout) {
        this.fragments = new ArrayList<>();
        this.context = context;
        this.linearLayout = linearLayout;
    }

    public void addAll(List<AppFragment> fragments) {
        for (AppFragment fragment : fragments) {
            add(fragment);
        }
    }

    public void add(AppFragment fragment) {
        fragments.add(fragment);

        FrameLayout frameLayout = new FrameLayout(context);

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(40, 20, 40, 20);

        frameLayout.setLayoutParams(layoutParams);


        linearLayout.addView(frameLayout);



        fragment.create(frameLayout);
    }
}
