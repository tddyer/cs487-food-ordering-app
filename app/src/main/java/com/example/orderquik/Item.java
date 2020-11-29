package com.example.orderquik;

import java.io.Serializable;

public class Item implements Serializable {

    private String name;
    private double price;
    private String description;
    private int calories;
    private int itemAmount;

    public Item() {}

    public Item(String name, double price, String desc, int cals) {
        this.name = name;
        this.price = price;
        this.description = desc;
        this.calories = cals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getItemAmount() { return itemAmount; }

    public void setItemAmount(int itemAmount) { this.itemAmount = itemAmount; }

}
