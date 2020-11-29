package com.example.orderquik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";

    private List<Item> menuItems = new ArrayList<>();
    private List<Item> itemsInCart = new ArrayList<>();
    private RecyclerView menuRecycler;
    private ItemsAdapter itemsAdapter;

    private Bundle bundle;
    private User user;

    private DatabaseHandler databaseHandler;

    static String[] foodNames = {
            "Cheeseburger",
            "Salad",
            "[SPECIAL] Spaghetti and Meatballs",
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

        // getting login information to determine if a guest or user logged in
        bundle = getIntent().getExtras();

        // user login
        if (bundle != null) {
            String email = bundle.getString("LOGIN_EMAIL");
            String pwrd = bundle.getString("LOGIN_PASSWORD");
            user = new User(email, pwrd);

        } else {
            // guest login
            user = new Guest();
        }

        menuRecycler = findViewById(R.id.menuRecycler);
        itemsAdapter = new ItemsAdapter(menuItems, this);
        menuRecycler.setAdapter(itemsAdapter);
        menuRecycler.setLayoutManager(new LinearLayoutManager(this));



        databaseHandler = new DatabaseHandler(this);

        // refresh menu items database when local list is updated - this would be completely different if we
        // had a cloud database implemented (menu items wouldn't be editable from a user device)
        databaseHandler.flushItems();

        // populating recycler with test data - this would be different if we had a cloud database
        // implemented (items would be fetched from database and loaded in instead of using a locally
        // stored list
        for (int i = 0; i < foodNames.length; i++) {
            Item it = new Item();
            it.setName(foodNames[i]);
            it.setPrice(foodPrices[i]);
            it.setDescription(foodDescriptions[i]);
            it.setCalories(foodCalories[i]);
            menuItems.add(it);
            databaseHandler.addItem(it);
        }

        itemsAdapter.notifyDataSetChanged();
    }

    // implementing functionality for onClick and onLongClick for the view holder

    // From OnClickListener
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
        final CharSequence[] amount = {"1","2","3","4","5"};
        final String[] tmpIndex = new String[1];


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please select the amount of dishes.")
                .setSingleChoiceItems(amount, -1, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int index){
                        tmpIndex[0] = null;
                        tmpIndex[0] = (String) amount[index];
                    }
                });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int pos = menuRecycler.getChildLayoutPosition(v);
                Item itemTmp = menuItems.get(pos);


                Item it = null;
                for(Item ci : itemsInCart){
                    if(ci.getName().equals(itemTmp.getName())){
                        it = ci;
                        break;
                    }
                }
                if(it != null){
                    int position = itemsInCart.indexOf(it);
                    int newItemAmount = Integer.parseInt(tmpIndex[0]);
                    int currentItemAmount = it.getItemAmount();
                    itemsInCart.get(pos).setItemAmount(newItemAmount+currentItemAmount);
                }else{
                    itemTmp.setItemAmount(Integer.parseInt(tmpIndex[0]));
                    itemsInCart.add(itemTmp);
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

//        int pos = menuRecycler.getChildLayoutPosition(v);
//        itemsInCart.add(menuItems.get(pos));
//        Toast.makeText(this.getApplicationContext(), menuItems.get(pos).getName()+" added", Toast.LENGTH_SHORT).show();
    }

    // From OnLongClickListener
    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
//        int pos = recyclerView.getChildLayoutPosition(v);
        return true;
    }

    // options menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuCart:
                viewCart();
                return true;
            case R.id.menuProfile:
                // TODO: view profile here
                viewAccount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void viewAccount(){
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        intent.putExtra("UserInFo", (Serializable) user);
        startActivity(intent);
    }

    // alert dialogs

    public void viewCart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] itemNames = new CharSequence[itemsInCart.size()];

        for (int i = 0; i < itemsInCart.size(); i++){
            itemNames[i] = itemsInCart.get(i).getName()+" (amount: "+itemsInCart.get(i).getItemAmount()+")";
        }


        builder.setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO: navigate to checkout activity here
                if(itemsInCart.size() == 0){
                    emptyCartAlert();
                }else{
                    Intent it = new Intent(MainActivity.this, CheckOutActivity.class);
                    it.putExtra("selectedItems", (Serializable) itemsInCart);
                    it.putExtra("user", (Serializable) user);
                    startActivity(it);
                }
            }
        });
        builder.setNegativeButton("Continue Shopping", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });

        builder.setTitle("Your Order");
        builder.setItems(itemNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO: remove items here
                itemsInCart.remove(which);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void emptyCartAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Cart is Empty.");

        builder.setNegativeButton("Continue Shopping", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
