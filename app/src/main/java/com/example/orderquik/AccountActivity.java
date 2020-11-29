package com.example.orderquik;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class AccountActivity extends AppCompatActivity {
    public User UserTMP;
    public TextView userEmail;public TextView userEmailText;
    public TextView userPW;public TextView userPWText;
    public TextView userRewardsPoints;public TextView userRewardsPointsText;

    public TextView orderHistory;public TextView orderHistoryText;
    public TextView creditCardInfo;public TextView creditCardInfoText;
    public TextView deliveryAddress;public TextView deliveryAddressText;

    public TextView userAccountText;
    public Button readyTimeBTN;

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


        if(getIntent().hasExtra("UserInFo")){
            UserTMP = (User) getIntent().getSerializableExtra("UserInFo");
            String getEMAIL = UserTMP.getEmail();

            if(getEMAIL.isEmpty() || getEMAIL.equals("") || getEMAIL.equals("null")){
                //If guest logined
                userEmail.setVisibility(View.INVISIBLE);userEmailText.setVisibility(View.INVISIBLE);
                userPW.setVisibility(View.INVISIBLE);userPWText.setVisibility(View.INVISIBLE);
                userRewardsPoints.setVisibility(View.INVISIBLE);
                userRewardsPointsText.setVisibility(View.INVISIBLE);
                orderHistory.setVisibility(View.INVISIBLE);orderHistoryText.setVisibility(View.INVISIBLE);
                creditCardInfo.setVisibility(View.INVISIBLE);creditCardInfoText.setVisibility(View.INVISIBLE);
                deliveryAddress.setVisibility(View.INVISIBLE);
                deliveryAddressText.setVisibility(View.INVISIBLE);

                userAccountText.setText("GUEST");
            }else{
                //If user logined
                userEmail.setText(UserTMP.getEmail());
                userPW.setText(UserTMP.getPassword());
                userRewardsPoints.setText(String.valueOf(UserTMP.getRewardPoints()));
            }
        }

        if(getIntent().hasExtra("done Order")){
            UserTMP = (User) getIntent().getSerializableExtra("done Order");
            String getEMAIL = UserTMP.getEmail();

            if(getEMAIL.isEmpty() || getEMAIL.equals("") || getEMAIL.equals("null")){
                //If guest logined
                userEmail.setVisibility(View.INVISIBLE);userEmailText.setVisibility(View.INVISIBLE);
                userPW.setVisibility(View.INVISIBLE);userPWText.setVisibility(View.INVISIBLE);
                userRewardsPoints.setVisibility(View.INVISIBLE);
                userRewardsPointsText.setVisibility(View.INVISIBLE);
                orderHistory.setVisibility(View.INVISIBLE);orderHistoryText.setVisibility(View.INVISIBLE);
                creditCardInfo.setVisibility(View.INVISIBLE);creditCardInfoText.setVisibility(View.INVISIBLE);
                deliveryAddress.setVisibility(View.INVISIBLE);
                deliveryAddressText.setVisibility(View.INVISIBLE);

                userAccountText.setText("GUEST");

            }else{
                //If user logined
                userEmail.setText(UserTMP.getEmail());
                userPW.setText(UserTMP.getPassword());
                userRewardsPoints.setText(String.valueOf(UserTMP.getRewardPoints()));

            }

        }

    }
    //view the expected time of order
    public void readytimdBTNclicked(View view){
        if(getIntent().hasExtra("done Order")){
            viewReadyTime();
        }else{
            orderAlert();
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
    public void orderAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The order has not yet been completed.");

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
            //super.onBackPressed();
        }else{
            super.onBackPressed();
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
