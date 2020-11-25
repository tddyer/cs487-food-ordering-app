package com.example.orderquik;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity{
    CharSequence[] itemNames = new CharSequence[0];
    public static RecyclerView checkoutRecycler;
    public static CheckoutItemsAdapter checkoutItemsAdapter;
    private List<Item> tmp = new ArrayList<>();
    //private CheckoutItem coTMP= new CheckoutItem();
    public static List<CheckoutItem> checkoutItems = new ArrayList<CheckoutItem>();
    private Button checkoutBTN;
    private CheckBox dineinChecked;
    private CheckBox pickupChecked;
    private CheckBox deliveryChecked;
    private TextView total;
    public Double totalAmount=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        checkoutBTN = findViewById(R.id.CheckOut);
        dineinChecked = findViewById(R.id.dineinCheckBox);
        pickupChecked = findViewById(R.id.pickupCheckBox);
        deliveryChecked = findViewById(R.id.deliveryCheckBox);
        total = findViewById(R.id.total);

        checkoutItems.clear();

        if(getIntent().hasExtra("selectedItems")){
            tmp = (ArrayList<Item>) getIntent().getSerializableExtra("selectedItems");
            for(int i=0;i<tmp.size();i++){
                CheckoutItem coTMP= new CheckoutItem();
                coTMP.setCoName(tmp.get(i).getName());

                CheckoutItem cItem = null;
                for(CheckoutItem ci : checkoutItems){
                    if(ci.getCoName().equals(coTMP.getCoName())){
                        cItem = ci;
                        break;
                    }
                }
                if(cItem != null){
                    int pos = checkoutItems.indexOf(cItem);
                    int newItemAmount = cItem.getItemTotal();
                    int currentItemAmount = tmp.get(i).getItemAmount();
                    checkoutItems.get(pos).setItemTotal(newItemAmount+currentItemAmount);
                }else{
                    coTMP.setItemTotal(tmp.get(i).getItemAmount());
                    coTMP.setCoPrice(tmp.get(i).getPrice());
                    checkoutItems.add(coTMP);
                }
                totalAmount += tmp.get(i).getPrice()*tmp.get(i).getItemAmount();
            }

        }
        total.setText("Total: $"+totalAmount.toString());
        checkoutRecycler = findViewById(R.id.checkoutRecycler);
        checkoutItemsAdapter = new CheckoutItemsAdapter(checkoutItems, this);
        checkoutRecycler.setAdapter(checkoutItemsAdapter);
        checkoutRecycler.setLayoutManager(new LinearLayoutManager(this));

        checkoutItemsAdapter.notifyDataSetChanged();
    }
    //Check out Button Clicked
    public void checkoutBTNclicked(View view){
        if(orderoptionChecked() != 1)
            checkOptionAlert();
        else{
            optionalSurveyAlert();
        }
    }
    public int orderoptionChecked(){
        int dineinCount=0;
        int pickupCount=0;
        int deliveryCount=0;

        if(dineinChecked.isChecked())
            dineinCount++;
        if(pickupChecked.isChecked())
            pickupCount++;
        if(deliveryChecked.isChecked())
            deliveryCount++;
        return (dineinCount+pickupCount+deliveryCount);
    }
    public void checkOptionAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please check only one option");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void optionalSurveyAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Optional Survey");

        builder.setPositiveButton("START", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                optionalSurvey();
            }
        });
        builder.setNegativeButton("SKIP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //Optional Survey
    public void optionalSurvey(){
        final CharSequence[] menu = MainActivity.foodNames;
        AlertDialog.Builder bd = new AlertDialog.Builder(this);
        bd.setTitle("Please choose the best dish");
        bd.setSingleChoiceItems(menu, -1, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int index){
                // TODO: save the survey response
            }
        });

        bd.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               // TODO: view the expected time of order page
            }
        });

        AlertDialog dialog = bd.create();
        dialog.show();
    }

}
