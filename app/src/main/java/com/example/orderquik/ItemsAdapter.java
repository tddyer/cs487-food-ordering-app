package com.example.orderquik;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<Item> itemList;
    private MainActivity mainActivity;

    public ItemsAdapter(List<Item> list, MainActivity ma) {
        this.itemList = list;
        this.mainActivity = ma;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        // create inflater from parent's context, then inflate it using the defined layout
        // for each list card
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);

        // calls appropriate functions from main activity when a click/long click is detected
        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new ItemViewHolder(itemView);
    }

    // sets data into view holder to match a Note object
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("$" + item.getPrice());
        holder.description.setText(item.getDescription());
        holder.calories.setText(item.getCalories() + " calories");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
