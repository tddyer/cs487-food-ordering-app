package com.example.orderquik;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CheckoutItemViewHolder extends RecyclerView.ViewHolder{
    TextView coName;
    TextView coPrice;
    TextView coTotal;


    CheckoutItemViewHolder(View view) {
        super(view);
        coName = view.findViewById(R.id.checkoutName);
        coPrice = view.findViewById(R.id.checkoutPrice);
        coTotal = view.findViewById(R.id.checkoutTotal);
    }

}
