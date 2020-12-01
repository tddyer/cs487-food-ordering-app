package com.example.orderquik;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AccountActivity extends AppCompatActivity {
    public User UserTMP;
    private ArrayList<CheckoutItem> tmp = new ArrayList<>();

    public TextView userEmail;public TextView userEmailText;
    public TextView userPW;public TextView userPWText;
    public TextView userRewardsPoints;public TextView userRewardsPointsText;

    public TextView orderHistory;public TextView orderHistoryText;
    public TextView creditCardInfo;public TextView creditCardInfoText;
    public TextView deliveryAddress;public TextView deliveryAddressText;

    public TextView userAccountText;
    public Button readyTimeBTN;
    public Button updateBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        userEmail = findViewById(R.id.userEmail);userEmailText = findViewById(R.id.userEmailText);
        userPW = findViewById(R.id.userPassword);userPWText = findViewById(R.id.userPasswordText);
        userRewardsPoints = findViewById(R.id.userRewardPoints);
        userRewardsPointsText = findViewById(R.id.userRewardPointsText);
        orderHistory = findViewById(R.id.orderHistory);orderHistoryText = findViewById(R.id.orderHistoryText);
        creditCardInfo = findViewById(R.id.creditCardInfo);creditCardInfoText = findViewById(R.id.creditcardInfoText);
        deliveryAddress = findViewById(R.id.deliveryAddress);
        deliveryAddressText = findViewById(R.id.deliveryAddressText);

        userAccountText = findViewById(R.id.userTextview);
        readyTimeBTN = findViewById(R.id.readyTimeBTN);
        updateBTN = findViewById(R.id.updateBTN);


        if(getIntent().hasExtra("UserInFo")){
            UserTMP = (User) getIntent().getSerializableExtra("UserInFo");
            String getEMAIL = UserTMP.getEmail();

            if(getEMAIL.isEmpty() || getEMAIL.equals("") || getEMAIL.equals("null")){
                //guest
                userEmail.setVisibility(View.INVISIBLE);userEmailText.setVisibility(View.INVISIBLE);
                userPW.setVisibility(View.INVISIBLE);userPWText.setVisibility(View.INVISIBLE);
                userRewardsPoints.setVisibility(View.INVISIBLE);
                userRewardsPointsText.setVisibility(View.INVISIBLE);
                orderHistory.setVisibility(View.INVISIBLE);orderHistoryText.setVisibility(View.INVISIBLE);
                creditCardInfo.setVisibility(View.INVISIBLE);creditCardInfoText.setVisibility(View.INVISIBLE);
                deliveryAddress.setVisibility(View.INVISIBLE);
                deliveryAddressText.setVisibility(View.INVISIBLE);
                updateBTN.setVisibility(View.INVISIBLE);

                userAccountText.setText("GUEST");
            }else{
                //user
                userEmail.setText(UserTMP.getEmail());
                userPW.setText(UserTMP.getPassword());
                userRewardsPoints.setText(String.valueOf(UserTMP.getRewardPoints()));
                orderHistory.setText(UserTMP.getOrderHistory());
                creditCardInfo.setText(UserTMP.getCreditCardInfo());
                deliveryAddress.setText(UserTMP.getDeliveryAddress());
            }
        }

        if(getIntent().hasExtra("done Order")){
            UserTMP = (User) getIntent().getSerializableExtra("done Order");
            String getEMAIL = UserTMP.getEmail();

            if(getEMAIL.isEmpty() || getEMAIL.equals("") || getEMAIL.equals("null")){
                //guest
                userEmail.setVisibility(View.INVISIBLE);userEmailText.setVisibility(View.INVISIBLE);
                userPW.setVisibility(View.INVISIBLE);userPWText.setVisibility(View.INVISIBLE);
                userRewardsPoints.setVisibility(View.INVISIBLE);
                userRewardsPointsText.setVisibility(View.INVISIBLE);
                orderHistory.setVisibility(View.INVISIBLE);orderHistoryText.setVisibility(View.INVISIBLE);
                creditCardInfo.setVisibility(View.INVISIBLE);creditCardInfoText.setVisibility(View.INVISIBLE);
                deliveryAddress.setVisibility(View.INVISIBLE);
                deliveryAddressText.setVisibility(View.INVISIBLE);
                updateBTN.setVisibility(View.INVISIBLE);

                userAccountText.setText("GUEST");

            }else{
                //user
                userEmail.setText(UserTMP.getEmail());
                userPW.setText(UserTMP.getPassword());
                userRewardsPoints.setText(String.valueOf(UserTMP.getRewardPoints()));
                orderHistory.setText(UserTMP.getOrderHistory());
                creditCardInfo.setText(UserTMP.getCreditCardInfo());
                deliveryAddress.setText(UserTMP.getDeliveryAddress());
            }

        }
        if(getIntent().hasExtra("done Order_order history")){
            long now = System.currentTimeMillis();
            Date mDate = new Date(now);
            SimpleDateFormat simpleDate = new SimpleDateFormat("MM-dd");
            String getTime = simpleDate.format(mDate);

            tmp = (ArrayList<CheckoutItem>) getIntent().getSerializableExtra("done Order_order history");

            String ohTMP = String.valueOf(orderHistory.getText());
            orderHistory.setText(ohTMP+"\n"+getTime+" "+ tmp.get(0).getCoName());

        }

    }
    //update information
    public void updateBTNclicked(View view){
        final String[] tmp = new String[1];

        final String[] update = {"Password","Credit Card Info", "Delivery Address"};
        AlertDialog.Builder bd = new AlertDialog.Builder(this);
        bd.setTitle("Update");
        bd.setSingleChoiceItems(update, -1, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int index){
                tmp[0] = update[index];
            }
        });

        bd.setPositiveButton("update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                viewUpdateAlert(tmp[0]);
            }
        });
        bd.setNegativeButton("Cancel", (dialog, id) -> {
            // do nothing
        });
        AlertDialog dialog = bd.create();
        dialog.show();
    }
    
    public void viewUpdateAlert(String tmp){
        final EditText txtEdit = new EditText(AccountActivity.this);

        android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(this);
        alertBuilder.setTitle("Update "+tmp);
        alertBuilder.setView(txtEdit);

        alertBuilder.setPositiveButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updateTMP = txtEdit.getText().toString();

                if(tmp.equals("Password")){
                    UserTMP.setPassword(updateTMP);
                    userPW.setText(updateTMP);

                }else if(tmp.equals("Credit Card Info")){
                    UserTMP.setCreditCardInfo(updateTMP);
                    creditCardInfo.setText(updateTMP);

                }else{
                    UserTMP.setDeliveryAddress(updateTMP);
                    deliveryAddress.setText(updateTMP);
                }

            }
        });
        alertBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alertBuilder.show();
        return;
    }

    //view the expected time of order
    public void readytimdBTNclicked(View view){
        if(getIntent().hasExtra("done Order")){
            viewReadyTime();
        }else{
            Toast.makeText(this, "The order has not been completed yet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewReadyTime(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("The expected time of order");
        builder.setMessage("Your order will be ready in 30 minutes");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if(getIntent().hasExtra("done Order")){
            Toast.makeText(this, "Your order has been completed.", Toast.LENGTH_SHORT).show();
        }else{
            super.onBackPressed();
            Intent it = new Intent(this, MainActivity.class);
            it.putExtra("AccountPageToCheckoutPage", (Serializable) UserTMP);
            startActivity(it);
        }

    }

    // options menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.link_to_login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.loginLink) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
