package com.nutriia.nutriia.adapters;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.nutriia.nutriia.Slide;

import java.util.List;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutriia.nutriia.R;

public class SlideAdapter extends PagerAdapter {

    private final Context context;
    private final List<Slide> slides;

    public SlideAdapter(Context context, List<Slide> slides) {
        this.context = context;
        this.slides = slides;
    }

    @Override
    public int getCount() {
        return this.slides.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Slide slide = this.slides.get(position);
        View view = LayoutInflater.from(this.context).inflate(R.layout.slide_tips_item, container, false);
        TextView title = view.findViewById(R.id.titleTextView);
        TextView description = view.findViewById(R.id.contentTextView);
        title.setText(slide.getTitle());
        description.setText(slide.getDescription());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
