package com.example.orderquik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class StaffPortal extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

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


        // filling active orders with test data
        //  - this would come from a database containing the current active orders normally
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

        activeOrdersAdapter.notifyDataSetChanged();

//        Log.d("STAFFPORTAL", "onCreate: \n" + staff.getStaffID());
    }

    public void completeOrder(int pos) {
        if (!orders.isEmpty()) {
            orders.remove(pos);
            activeOrdersAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        // do nothing
        Log.d("HERE", "onClick: ");
    }

    @Override
    public boolean onLongClick(View view) {
        Log.d("HERE", "onLongClick: ");
        int pos = activeOrdersRecycler.getChildLayoutPosition(view);
        confirmOrderCompletion(pos);
        return true;
    }

    // alert dialogs

    public void confirmOrderCompletion(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", (dialog, id) -> completeOrder(pos));
        builder.setNegativeButton("CANCEL", (dialog, id) -> {
            // do nothing
        });
        builder.setMessage("Are you sure you want to mark this order as complete?");
        builder.setTitle("Complete Order");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}