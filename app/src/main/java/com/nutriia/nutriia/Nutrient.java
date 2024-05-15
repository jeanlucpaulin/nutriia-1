package com.nutriia.nutriia;

public class Nutrient {
    private String name;
    private int value;

    private String unit;

    private int progress;

    public Nutrient(String name, int value, String unit) {
        this.name = name;
        this.value = value;
        this.unit = unit;
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
}
