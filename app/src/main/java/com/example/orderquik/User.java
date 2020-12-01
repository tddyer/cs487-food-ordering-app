package com.example.orderquik;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {

    private String email;
    private String password;
    private int rewardPoints;
    private String orderHistory;
    private String creditCardInfo;
    private String deliveryAddress;

    public User() {this.email = "";}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.rewardPoints = new Random().nextInt(1000);
        this.orderHistory = "11-15 Cheeseburger";
        this.creditCardInfo = "CitiBank **********";
        this.deliveryAddress = "3241 S Wabash Ave, Chicago, IL 60616, United States";
    }

    public User(String email, String password, int points, String lastOrder, String addr, String card) {
        this.email = email;
        this.password = password;
        this.rewardPoints = points;
        this.orderHistory = lastOrder;
        this.deliveryAddress = addr;
        this.creditCardInfo = card;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getRewardPoints(){ return rewardPoints; }
    public void setRewardPoints(int rewardPoints) { this.rewardPoints = rewardPoints; }

    public String getOrderHistory() {
        return orderHistory;
    }
    public void setOrderHistory(String orderHistory) {
        this.orderHistory = orderHistory;
    }

    public String getCreditCardInfo() {
        return creditCardInfo;
    }
    public void setCreditCardInfo(String creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
