package com.example.orderquik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class StaffPortal extends AppCompatActivity {

    private Bundle bundle;
    private StaffMember staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_portal);

        // getting login information to determine if a guest or user logged in
        bundle = getIntent().getExtras();

        // staff login
        if (bundle != null) {
            int staffID = bundle.getInt("LOGIN_ID");
            String pwrd = bundle.getString("LOGIN_PASSWORD");
            staff = new StaffMember(staffID, pwrd);
        }

        Log.d("STAFFPORTAL", "onCreate: \n" + staff.getStaffID());
    }
}