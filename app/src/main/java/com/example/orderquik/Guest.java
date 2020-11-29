package com.example.orderquik;

import java.io.Serializable;
import java.util.Random;

public class Guest extends User implements Serializable {

    // if a database was in use, this id would be a unique primary key for current active guests in the system
    private int guestId;

    public Guest() {
        this.guestId = new Random().nextInt(1000);
    }

    public int getGuestId() {
        return guestId;
    }
}
