package com.nutriia.nutriiaemf.adapters;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutriia.nutriiaemf.models.Slide;

import java.util.List;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutriia.nutriiaemf.R;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private final Context context;
    private final List<Slide> slides;

    public SlideAdapter(Context context, List<Slide> slides) {
        this.context = context;
        this.slides = slides;
    }

    public static class SlideViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;

        public SlideViewHolder(@NonNull View itemView) {
            //Creation of the view holder from the layout of the slide item
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.contentTextView);
        }
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slide_tips_item, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        //Binding of the data to the view holder
        Slide slide = slides.get(position);
        holder.title.setText(slide.getTitle());
        holder.description.setText(slide.getDescription());
    }

    @Override
    public int getItemCount() {
        return slides.size();
    }
}