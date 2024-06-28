package com.nutriia.nutriiaemf.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.nutriia.nutriiaemf.R;

/**
 * FormationBanner class
 * This class is used to create a formation banner fragment
 */
public class FormationBanner extends AppFragment {

    @Override
    public void create(FrameLayout frameLayout) {
        Context context = frameLayout.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.component_formation_banner, frameLayout, false);

        frameLayout.addView(view);
    }
}

