package com.nutriia.nutriiaemf.adapters;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nutriia.nutriiaemf.fragments.AppFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentsRDAAdapter {
    private List<AppFragment> fragments;

    private Context context;

    private LinearLayout linearLayout;

    public FragmentsRDAAdapter(Context context, LinearLayout linearLayout) {
        this.fragments = new ArrayList<>();
        this.context = context;
        this.linearLayout = linearLayout;
    }

    /**
     * Add all fragments to the layout
     * @param fragments
     */
    public void addAll(List<AppFragment> fragments) {
        for (AppFragment fragment : fragments) {
            add(fragment);
        }
    }

    /**
     * Add a fragment to the layout
     * @param fragment
     */
    public void add(AppFragment fragment) {
        fragments.add(fragment);

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        linearLayout.addView(frameLayout);

        fragment.create(frameLayout);
    }
}
