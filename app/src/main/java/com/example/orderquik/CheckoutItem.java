package com.example.orderquik;

import java.io.Serializable;

public class CheckoutItem implements Serializable {
    private String coName;
    private double coPrice;
    private int itemTotal;


    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public double getCoPrice() {
        return coPrice;
    }

    public void setCoPrice(double coPrice) {
        this.coPrice = coPrice;
    }

    public int getItemTotal() { return itemTotal; }

    public void setItemTotal(int itemTotal) { this.itemTotal = itemTotal; }

}
