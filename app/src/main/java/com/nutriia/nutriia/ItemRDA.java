package com.nutriia.nutriia;

import com.nutriia.nutriia.interfaces.IItemRDA;

public class ItemRDA implements IItemRDA {

    private final String name;
    private final String unit;
    private final int amount;

    public ItemRDA(String name, String unit, int amount) {
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public int getAmount() {
        return amount;
    }

}
