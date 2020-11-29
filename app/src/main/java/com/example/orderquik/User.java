package com.example.orderquik;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {

    private String email;
    private String password;
    private int rewardPoints;

    public User() {this.email = "";}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.rewardPoints = new Random().nextInt(1000);
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
}
