package com.example.orderquik;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckoutItemsAdapter extends RecyclerView.Adapter<CheckoutItemViewHolder> {

    private List<CheckoutItem> coItem;
    private CheckOutActivity ca;

    CheckoutItemsAdapter(List<CheckoutItem> checkoutitem, CheckOutActivity coActivity) {
        this.coItem = checkoutitem;
        this.ca = coActivity;
    }

    @NonNull
    @Override
    public CheckoutItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View checkoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item, parent, false);
        return new CheckoutItemViewHolder(checkoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutItemViewHolder holder, int position) {
        CheckoutItem item = coItem.get(position);
        holder.coName.setText(item.getCoName());
        holder.coPrice.setText("$" + item.getCoPrice());
        holder.coTotal.setText("Count: "+String.valueOf(item.getItemTotal()));
    }

    @Override
    public int getItemCount() {
        return coItem.size();
    }
}
