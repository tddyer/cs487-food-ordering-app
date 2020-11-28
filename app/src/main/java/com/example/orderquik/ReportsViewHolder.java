package com.example.orderquik;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ReportsViewHolder extends RecyclerView.ViewHolder {

    TextView itemName;
    TextView itemRating;

    public ReportsViewHolder(View view) {
        super(view);
        itemName = view.findViewById(R.id.itemName);
        itemRating = view.findViewById(R.id.itemRating);
    }
}
