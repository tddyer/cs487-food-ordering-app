package com.example.orderquik;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private TextView userRewardPoints;
    private Switch rewardpointsSwitch;

    public Double totalAmount=0.0;
    public int rewardPointsTMP=0;

    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        checkoutBTN = findViewById(R.id.CheckOut);
        dineinChecked = findViewById(R.id.dineinCheckBox);
        pickupChecked = findViewById(R.id.pickupCheckBox);
        deliveryChecked = findViewById(R.id.deliveryCheckBox);
        userRewardPoints = findViewById(R.id.showRewardPoints);
        rewardpointsSwitch = findViewById(R.id.rewardPointUse);
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
        if(getIntent().hasExtra("user")){
            user = (User) getIntent().getSerializableExtra("user");
            if(user.getEmail().isEmpty() || user.getEmail().equals("") || user.getEmail().equals("null")){
                userRewardPoints.setText("N/A");
                rewardpointsSwitch.setVisibility(View.INVISIBLE);
            }else{
                rewardPointsTMP = user.getRewardPoints();
                userRewardPoints.setText(String.valueOf(rewardPointsTMP));
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
            if(rewardpointsSwitch.isChecked()){
                //when user prefers to user reward points
                useRewardPoints();
            }else{
                checkoutAlert();
            }

        }
    }
    public void useRewardPoints(){
        int points = user.getRewardPoints();
        if(points<100){
            rewardPointsAlert(); //can not use rewards points
        }else{
            rewardPointsAppliedAlert();
        }
    }
    public void checkoutAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check out Alert");
        builder.setMessage("Your reward points are not applied. Total: $"+totalAmount);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                optionalSurveyAlert();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void rewardPointsAppliedAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check out Alert");
        builder.setMessage("Your reward points are applied. Total: $"+(totalAmount-rewardPointsTMP/100));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                totalAmount = totalAmount-Math.floor(rewardPointsTMP/100);
                rewardPointsTMP = (int) (rewardPointsTMP - Math.floor(rewardPointsTMP/100));
                user.setRewardPoints(rewardPointsTMP);
                optionalSurveyAlert();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void rewardPointsAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your reward points is below 100. Can not use it at this point.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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

                if(user.getEmail().isEmpty() || user.getEmail().equals("") || user.getEmail().equals("null")){
                    optionalSurvey();
                }else{
                    //if they use their accounts, they will also get rewards points, 2points per dollar spent
                    double tmp = Math.floor(totalAmount);
                    int AddRewardPoints = ((int) tmp)*2;
                    userRewardPoints.setText(String.valueOf(rewardPointsTMP+AddRewardPoints));
                    optionalSurvey();
                }

            }
        });
        builder.setNegativeButton("SKIP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO: go to the account page, and customers can view the expected time of order
                viewAccount();
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
               // TODO: go to the account page, and customers can view the expected time of order
                viewAccount();
            }
        });

        AlertDialog dialog = bd.create();
        dialog.show();
    }

    //go to the account page, and customers can view the expected time of order page
    public void viewAccount(){
        Intent intent = new Intent(CheckOutActivity.this, AccountActivity.class);
        intent.putExtra("done Order", (Serializable) user);
        startActivity(intent);
    }
}
