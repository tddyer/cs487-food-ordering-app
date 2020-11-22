package com.example.orderquik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";

    private List<Item> menuItems = new ArrayList<>();
    private RecyclerView menuRecycler;
    private ItemsAdapter itemsAdapter;

    String[] foodNames = {
            "Cheeseburger",
            "Salad",
            "Spaghetti and Meatballs",
            "Gyros",
            "Lasagna",
            "Macaroni and Cheese",
            "Chicken Fried Rice",
            "Sloppy Joes",
            "Beef and Broccoli Stir-Fry",
            "Sweet & Sour Chicken",
            "Lemon Baked Cod"
    };

    double[] foodPrices = {
            10.99,
            7.99,
            12.99,
            14.99,
            15.99,
            9.99,
            11.99,
            9.99,
            12.99,
            13.99,
            17.99
    };

    String[] foodDescriptions = {
            "A classic angus burger topped with cheddar cheese, lettuce, tomato, and pickles.",
            "Romaine lettuce, baby spinach, carrots, cucumbers, shredded cheese, and your choice of dressing.",
            "Classic italian pasta with our chef's specialty marinara sauce and homemade meatballs.",
            "A mix of ground beef and ground lamb with onion, garlic, and rosemary with a hot pita.",
            "Our signature 4-cheese lasagne topped with a light layer of marinara meat sauce.",
            "One-of-a-kind homemade mac-n-cheese with a mixture of 3 cheeses.",
            "Two Crispy, fried chicken breasts with a side of mashed potatos and gravy.",
            "Ground turkey topped with out Homemade 'joe' sauce",
            "Lean beef with steamed broccoli, a mixture of vegetables, and your choice of stir-fry sauce.",
            "Our truly sweet and somewhat sour sauce served with a bowl of steamed white rice.",
            "Atlantic cod baked with lemon flavor."
    };

    int[] foodCalories = {
            1400,
            875,
            1450,
            1700,
            1175,
            1350,
            1500,
            1100,
            1200,
            1300,
            950
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuRecycler = findViewById(R.id.menuRecycler);
        itemsAdapter = new ItemsAdapter(menuItems, this);
        menuRecycler.setAdapter(itemsAdapter);
        menuRecycler.setLayoutManager(new LinearLayoutManager(this));

        // populating recycler with test data
        for (int i = 0; i < foodNames.length; i++) {
            Item it = new Item();
            it.setName(foodNames[i]);
            it.setPrice(foodPrices[i]);
            it.setDescription(foodDescriptions[i]);
            it.setCalories(foodCalories[i]);
            menuItems.add(it);
        }

        itemsAdapter.notifyDataSetChanged();
    }

    // implementing functionality for onClick and onLongClick for the view holder

    // From OnClickListener
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
//        int pos = recyclerView.getChildLayoutPosition(v);
//        Note n = notesList.get(pos);
//        launchExistingEditActivity(n, pos);
    }

    // From OnLongClickListener
    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
//        int pos = recyclerView.getChildLayoutPosition(v);
        return true;
    }
}