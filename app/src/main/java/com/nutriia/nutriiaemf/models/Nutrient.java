package com.nutriia.nutriiaemf.models;

import android.content.Context;

public class Nutrient {
    private String name;

    /**
     * Goal value of the nutrient
     */
    private int value;

    /**
     * Unit of the nutrient
     */
    private String unit;

    /**
     * Progress of the nutrient
     */
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

    /**
     * Get the goal value of the nutrient
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the unit of the nutrient
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Get the progress of the nutrient
     * @return
     */
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
