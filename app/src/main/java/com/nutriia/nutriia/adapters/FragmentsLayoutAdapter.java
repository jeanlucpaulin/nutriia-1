package com.nutriia.nutriia.adapters;

import android.content.Context;
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
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(frameLayout);



        fragment.create(frameLayout);
    }
}
