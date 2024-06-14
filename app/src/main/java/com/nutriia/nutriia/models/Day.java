package com.nutriia.nutriia.models;

import androidx.annotation.NonNull;

import java.util.Map;

public class Day {
    private Nutrient calorie;
    private Nutrient fiber;

    private Map<String, Nutrient> macroNutrients;
    private Map<String, Nutrient> microNutrients;

    private String analysis;

    public Day(Nutrient calorie, Nutrient fiber, Map<String, Nutrient> macroNutrients, Map<String, Nutrient> microNutrients) {
        this.calorie = calorie;
        this.fiber = fiber;
        this.macroNutrients = macroNutrients;
        this.microNutrients = microNutrients;
        this.analysis = "";
    }

    public Day(Nutrient calorie, Nutrient fiber, Map<String, Nutrient> macroNutrients, Map<String, Nutrient> microNutrients, String analysis) {
        this.calorie = calorie;
        this.fiber = fiber;
        this.macroNutrients = macroNutrients;
        this.microNutrients = microNutrients;
        this.analysis = analysis;
    }

    public Nutrient getCalorie() {
        return calorie;
    }

    public Nutrient getFiber() {
        return fiber;
    }

    public Map<String, Nutrient> getMacroNutrients() {
        return macroNutrients;
    }

    public Map<String, Nutrient> getMicroNutrients() {
        return microNutrients;
    }

    public String getAnalysis() {
        return analysis;
    }

    @NonNull
    @Override
    public String toString() {
        return "Day{" +
                "calorie=" + calorie +
                ", macroNutrients=" + macroNutrients +
                ", microNutrients=" + microNutrients +
                '}';
    }
}
