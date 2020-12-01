package com.example.orderquik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    public static final int LOGIN = 1;
    public static final int SIGNUP = 2;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHandler = new DatabaseHandler(this);

        // refreshing database
//        this.deleteDatabase("OrderQuikDB");


        /* ----------- USERS DATABASE TEST DATA CREATION + RETRIEVAL TESTING ----------- */


//        // adding test users to database
//        User u1 = new User("jsmith@gmail.com", "abc123", "3302 Yellow Rd, New York, NY, 10330", "2287349059230087", 200, "11-17 Cheeseburger, Salad, Gyros");
//        User u2 = new User("loganb22@gmail.com", "loganb123", "441 State St, Boston, MA, 33409", "7763409867113643", 80, "10-22 Beef and Broccoli Stir-Fry, Salad");
//        User u3 = new User("sashayarns@yahoo.com", "kittengirl!", "97431 Bluebird Ln, Austin, TX, 55609", "1103448967204789", 0, "");
//
//        databaseHandler.addUser(u1);
//        databaseHandler.addUser(u2);
//        databaseHandler.addUser(u3);
//
//        // fetching user data test
//        User temp = databaseHandler.loadUser("jsmith@gmail.com", "abc123");
//        Log.d("LOGINACTIVITY", "onCreate: " + temp.getDeliveryAddress());
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.d("AFRTERTEXT", "afterTextChanged: CHECKING INPUT");
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.d("AFRTERTEXT", "afterTextChanged: CHECKING INPUT");
            EditText password = (EditText)  findViewById(R.id.signupPassword);
            EditText confirm = (EditText) findViewById(R.id.confirmPasswordEditText);
            String pwrd = String.valueOf(password.getText());
            String toConfirm = String.valueOf(confirm.getText());

            if (!pwrd.equals(toConfirm)) {
                confirm.setError("Error: passwords must match.");
            }
        }
    };

    @SuppressLint("NonConstantResourceId")
    public void login(View v) {
        switch (v.getId()) {
            case R.id.userLogin: // launch main activity with login_id as entered email and password
                // account login
                userLogin();
                break;
            case R.id.guestLogin: // launch main activity without login info
                guestLogin();
                break;
            case R.id.staffLogin: // launch staff portal with valid staff credentials
                // staff login
                staffLogin();
                break;
            default:
                // do nothing
                break;
        }
    }

    public void signup(View v) {
        userSignup(false);
    }

    public void guestLogin() {
        // navigate to main activity with guest login info
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void userLogin() {

        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.user_login_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("User Login");

        builder.setPositiveButton("Login", (dialog, id) -> {
            EditText em = view.findViewById(R.id.textEmail);
            EditText password = view.findViewById(R.id.textPassword);

            String email;
            String pwrd;

            try {
                email = String.valueOf(em.getText());
                pwrd = String.valueOf(password.getText());

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("ID", LOGIN);
                intent.putExtra("LOGIN_EMAIL", email);
                intent.putExtra("LOGIN_PASSWORD", pwrd);
                startActivity(intent);

            } catch (NullPointerException npe) {
                npe.printStackTrace();
                userLogin(); // could re-display with some error message upon invalid login
            }

        });

        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // do nothing
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void staffLogin() {

        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.staff_login_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Staff Login");

        builder.setPositiveButton("Login", (dialog, id) -> {
            EditText em = view.findViewById(R.id.textID);
            EditText password = view.findViewById(R.id.textPassword);

            int staffID = -1;
            String pwrd;

            try {
                if (em.getText().toString().length() > 0)
                    staffID = Integer.parseInt(em.getText().toString());

                pwrd = String.valueOf(password.getText());

                if (staffID > -1) {
                    Intent intent = new Intent(this, StaffPortal.class);
                    intent.putExtra("LOGIN_ID", staffID);
                    intent.putExtra("LOGIN_PASSWORD", pwrd);
                    startActivity(intent);
                } else {
                    throw new Exception();
                }

            } catch (Exception e) {
                e.printStackTrace();
                staffLogin(); // could re-display with some error message upon invalid login
            }

        });

        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // do nothing
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void userSignup(boolean invalid) {



        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.user_signup_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("User Signup");

        EditText em = view.findViewById(R.id.textEmail);
        EditText password = view.findViewById(R.id.signupPassword);
        EditText confirmPassword = view.findViewById(R.id.confirmPasswordEditText);
        EditText address = view.findViewById(R.id.addressEdit);
        EditText cardNo = view.findViewById(R.id.creditEdit);

        if (invalid) {
            Log.d("LOGINACTIVITY", "userSignup: INVALID PASSWRDS");
            confirmPassword.setError("Error: passwords must match. Please try again.");
        }


        builder.setPositiveButton("Signup", (dialog, id) -> {

            String email;
            String pwrd;
            String confirmPwrd; // TODO: If time, do input checking here
            String addr;
            String card;

            try {
                email = String.valueOf(em.getText());
                pwrd = String.valueOf(password.getText());
                confirmPwrd = String.valueOf(confirmPassword.getText());
                addr = String.valueOf(address.getText());
                card = String.valueOf(cardNo.getText());

                if (confirmPwrd.equals(pwrd)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("ID", SIGNUP);
                    intent.putExtra("SIGNUP_EMAIL", email);
                    intent.putExtra("SIGNUP_PASSWORD", pwrd);
                    intent.putExtra("SIGNUP_ADDR", addr);
                    intent.putExtra("SIGNUP_CARD", card);
                    LoginActivity.this.startActivity(intent);
                } else {
                    Log.d("TAG", "userSignup: PASSWORDS DONT MATCH");
                    LoginActivity.this.userSignup(true);
                }

            } catch (NullPointerException npe) {
                npe.printStackTrace();
                LoginActivity.this.userSignup(true);
            }

        });

        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // do nothing
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}