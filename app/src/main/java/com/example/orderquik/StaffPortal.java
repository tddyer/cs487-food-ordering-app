package com.example.orderquik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class StaffPortal extends AppCompatActivity {

    private Bundle bundle;
    private StaffMember staff;

    private ArrayList<ArrayList<CheckoutItem>> orders = new ArrayList<>();

    private RecyclerView activeOrdersRecycler;
    private ActiveOrdersAdapter activeOrdersAdapter;

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

        // active orders recycler setup
        activeOrdersRecycler = findViewById(R.id.activeOrdersRecycler);
        activeOrdersAdapter = new ActiveOrdersAdapter(orders, this);
        activeOrdersRecycler.setAdapter(activeOrdersAdapter);
        activeOrdersRecycler.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 5; i++) {
            ArrayList<CheckoutItem> order = new ArrayList<>();
            switch (i) {
                case 0:
                    order.add(new CheckoutItem("Cheeseburger", 9.99, 2));
                    order.add(new CheckoutItem("Spaghetti and Meatballs", 12.99, 1));
                    order.add(new CheckoutItem("Steak", 21.99, 1));
                    order.add(new CheckoutItem("Soda", 3.99, 4));
                    break;
                case 1:
                    order.add(new CheckoutItem("Mac & Cheese", 9.99, 1));
                    order.add(new CheckoutItem("Lasagna", 10.99, 1));
                    order.add(new CheckoutItem("Water", 0.99, 2));
                    break;
                case 2:
                    order.add(new CheckoutItem("Cheeseburger", 9.99, 3));
                    order.add(new CheckoutItem("Soda", 3.99, 2));
                    order.add(new CheckoutItem("Water", 0.99, 1));
                    break;
                case 3:
                    order.add(new CheckoutItem("Lemon Baked Cod", 13.99, 1));
                    order.add(new CheckoutItem("Sweet & Sour Chicken", 11.99, 1));
                    order.add(new CheckoutItem("Beef and Broccoli Stir-fry", 13.99, 1));
                    order.add(new CheckoutItem("Soda", 3.99, 1));
                    order.add(new CheckoutItem("Sweet Tea", 4.99, 1));
                    order.add(new CheckoutItem("Water", 0.99, 1));
                    break;
                case 4:
                    order.add(new CheckoutItem("Gyros", 14.99, 1));
                    order.add(new CheckoutItem("Cheeseburger", 9.99, 2));
                    order.add(new CheckoutItem("Chicken Fried Rice", 9.99, 1));
                    order.add(new CheckoutItem("Water", 0.99, 3));
                    break;
            }

            orders.add(order);
        }

        // populating recycler with test data
//        for (int i = 0; i < 5; i++) {
//            ArrayList<CheckoutItem> order = new ArrayList<>();
//            for (int j = 0; j < 2; j++) {
//                CheckoutItem it = new CheckoutItem();
//                it.setCoName("Item " + j);
//                it.setCoPrice((j + 1) * 4);
//                it.setItemTotal(j + 1);
//                order.add(it);
//            }
//            orders.add(order);
//        }

        activeOrdersAdapter.notifyDataSetChanged();

//        Log.d("STAFFPORTAL", "onCreate: \n" + staff.getStaffID());
    }
}