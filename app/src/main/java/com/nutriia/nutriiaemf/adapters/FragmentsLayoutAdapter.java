package com.nutriia.nutriiaemf.adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nutriia.nutriiaemf.fragments.AppFragment;
import com.nutriia.nutriiaemf.fragments.FormationBanner;

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

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        //Add style to the fragment
        if (fragment instanceof FormationBanner){
            layoutParams.setMargins(0, 0, 0, 0);
        } else layoutParams.setMargins(40, 20, 40, 20);

        frameLayout.setLayoutParams(layoutParams);

        //Add the fragment to the layout
        linearLayout.addView(frameLayout);


        //create the fragment
        fragment.create(frameLayout);
    }
}
