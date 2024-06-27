package com.nutriia.nutriiaemf.models;

import android.content.Context;

public class Nutrient {
    private String name;
    private int value;

    private String unit;

    private int progress;

    public Nutrient(String name, int value, String unit) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.progress = 0;
    }

    public Nutrient(String name, int value, String unit, int progress) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public int getProgress() {
        return progress;
    }

    public static String getNutrientInfo(Context context, String nutrientName) {
        int resId = context.getResources().getIdentifier("info_" + nutrientName.toLowerCase().replace(" ", "_"), "string", context.getPackageName());
        if (resId != 0) {
            return context.getString(resId);
        } else {
            return "Information non disponible pour ce nutriment.";
        }
    }
}
