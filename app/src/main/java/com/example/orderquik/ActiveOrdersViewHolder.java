package com.example.orderquik;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ActiveOrdersViewHolder extends RecyclerView.ViewHolder {

        TextView orderTitle;
        TextView itemNames;

        ActiveOrdersViewHolder(View view) {
            super(view);
            orderTitle = view.findViewById(R.id.orderTitle);
            itemNames = view.findViewById(R.id.orderItems);
        }
}
