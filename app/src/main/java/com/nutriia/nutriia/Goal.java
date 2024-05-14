package com.nutriia.nutriia;

public class Goal {
    private String name;
    private boolean isActual;

    private String icon;

    public Goal(String name, String icon, boolean isActual) {
        this.name = name;
        this.icon = icon;
        this.isActual = isActual;
    }

    public Goal(String goalName, String icon) {
        this(goalName, icon, false);
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isActual() {
        return isActual;
    }
}
