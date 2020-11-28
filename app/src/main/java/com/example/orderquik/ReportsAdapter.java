package com.example.orderquik;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsViewHolder> {

    private ArrayList<HashMap<String, Integer>> itemRatings;
    private StaffPortal staffPortal;

    public ReportsAdapter(ArrayList<HashMap<String, Integer>> ratings, StaffPortal sp) {
        this.itemRatings = ratings;
        this.staffPortal = sp;
    }

    @NonNull
    @Override
    public ReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);

        // calls appropriate functions from main activity when a click/long click is detected
        itemView.setOnClickListener(staffPortal);
        itemView.setOnLongClickListener(staffPortal);

        return new ReportsViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReportsViewHolder holder, int position) {
        HashMap<String, Integer> rating = itemRatings.get(position);
        for (Map.Entry<String, Integer> entry : rating.entrySet()) {
            holder.itemName.setText(entry.getKey());
            holder.itemRating.setText(entry.getValue().toString());
        }
    }

    @Override
    public int getItemCount() {
        return itemRatings.size();
    }
}
