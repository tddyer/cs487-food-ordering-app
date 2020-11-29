package com.example.orderquik;

import java.util.ArrayList;

public class Order {

    private int id;
    private ArrayList<CheckoutItem> items;

    public Order() {}

    public Order(int id, ArrayList<CheckoutItem> items) {
        this.id = id;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CheckoutItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CheckoutItem> items) {
        this.items = items;
    }
}
