package com.example.orderquik;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiveOrdersAdapter extends RecyclerView.Adapter<ActiveOrdersViewHolder> {

    private ArrayList<ArrayList<CheckoutItem>> orders; // list of orders (which are lists of items)
    private StaffPortal staffPortal;
    HashMap<ArrayList<CheckoutItem>, Integer> orderIDs;

    ActiveOrdersAdapter(ArrayList<ArrayList<CheckoutItem>> orders, HashMap<ArrayList<CheckoutItem>, Integer> orderIDs, StaffPortal sp) {
        this.orders = orders;
        this.orderIDs = orderIDs;
        this.staffPortal = sp;
    }

    @NonNull
    @Override
    public ActiveOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_order_item, parent, false);

        v.setOnClickListener(staffPortal);
        v.setOnLongClickListener(staffPortal);
        
        return new ActiveOrdersViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ActiveOrdersViewHolder holder, int position) {

        StringBuilder currentOrderNames = new StringBuilder();
        ArrayList<CheckoutItem> order = orders.get(position);

        // each recycler item is a single order with multi-line strings containing info for the
        // items within that order
        for (CheckoutItem item : order) {
            currentOrderNames.append(String.format("(%dx) %s", item.getItemTotal(), item.getCoName()));

            if (order.indexOf(item) != order.size() - 1) {
                currentOrderNames.append("\n");
            }
        }

        int idStr = orderIDs.get(order);
        holder.orderTitle.setText("Order #" + Integer.toString(idStr));
        holder.itemNames.setText(currentOrderNames);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
