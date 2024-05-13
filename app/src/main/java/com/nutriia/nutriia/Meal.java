package com.nutriia.nutriia;

import androidx.annotation.NonNull;

import java.util.List;

public class Meal {
    private String name;
    private String content;

    public Meal(String name) {
        this.name = name;
        this.content = "";
    }

    public Meal(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
