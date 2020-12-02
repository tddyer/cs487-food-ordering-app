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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StaffPortal extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {

    private Bundle bundle;
    private StaffMember staff;

    private ArrayList<ArrayList<CheckoutItem>> orders = new ArrayList<>();
    private ArrayList<HashMap<String, Integer>> itemRatings = new ArrayList<>();
    private ArrayList<Order> databaseOrders = new ArrayList<>();
    private HashMap<ArrayList<CheckoutItem>, Integer> orderIDs = new HashMap<>();

    private RecyclerView activeOrdersRecycler;
    private ActiveOrdersAdapter activeOrdersAdapter;

    private RecyclerView reportsRecycler;
    private ReportsAdapter reportsAdapter;

    DatabaseHandler databaseHandler;

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
        activeOrdersAdapter = new ActiveOrdersAdapter(orders, orderIDs, this);

        activeOrdersRecycler.setAdapter(activeOrdersAdapter);
        activeOrdersRecycler.setLayoutManager(new LinearLayoutManager(this));

        // ratings recycler setup
        reportsRecycler = findViewById(R.id.reportsRecycler);
        reportsAdapter = new ReportsAdapter(itemRatings, this);

        reportsRecycler.setAdapter(reportsAdapter);
        reportsRecycler.setLayoutManager(new LinearLayoutManager(this));


//        this.deleteDatabase("OrderQuikDB");

        databaseHandler = new DatabaseHandler(this);


        // fetching active orders from database
        databaseOrders = databaseHandler.loadActiveOrders();

        for (Order o : databaseOrders)
            Log.d("PORAL", "onCreate: " + o.getItems());

        orders.clear();
        for (Order o : databaseOrders) {
            orders.add(o.getItems());
            orderIDs.put(o.getItems(), o.getId());
        }


        activeOrdersAdapter.notifyDataSetChanged();


        for (int i = 0; i < 5; i++) {
            HashMap<String, Integer> rating = new HashMap<>();
            switch (i) {
                case 0:
                    rating.put("Cheeseburger", 9);
                    break;
                case 1:
                    rating.put("Lasagna", 2);
                    break;
                case 2:
                    rating.put("Gyros", 4);
                    break;
                case 3:
                    rating.put("Lemon Baked Cod", 14);
                    break;
                case 4:
                    rating.put("Sweet & Sour Chicken", 8);
                    break;
            }

            itemRatings.add(rating);
        }

        Collections.sort(itemRatings, new RatingsComparator());

        reportsAdapter.notifyDataSetChanged();


//        Log.d("STAFFPORTAL", "onCreate: \n" + staff.getStaffID());
    }

    public void completeOrder(int pos) {
        if (!orders.isEmpty()) {
            databaseHandler.deleteOrder(orderIDs.get(orders.get(pos)));
            orderIDs.remove(orders.get(pos));
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