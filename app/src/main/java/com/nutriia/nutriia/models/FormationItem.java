package com.nutriia.nutriia.models;

public class FormationItem {
    private String url;
    private String title;
    private int icon;

    public FormationItem(String url, String title, int icon) {
        this.url = url;
        this.title = title;
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
