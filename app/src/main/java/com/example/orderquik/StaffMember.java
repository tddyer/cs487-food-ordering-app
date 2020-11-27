package com.example.orderquik;

public class StaffMember {

    private int staffID;
    private String password;

    public StaffMember (int id, String password) {
        this.staffID = id;
        this.password = password;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
