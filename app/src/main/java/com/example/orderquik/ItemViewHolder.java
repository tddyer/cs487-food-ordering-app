package com.example.orderquik;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView price;
    TextView description;
    TextView calories;

    ItemViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.itemName);
        price = view.findViewById(R.id.itemPrice);
        description = view.findViewById(R.id.itemDescription);
        calories = view.findViewById(R.id.itemCalories);
    }

}
