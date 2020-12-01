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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        userSignup();
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

    public void userSignup() {

        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.user_signup_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("User Signup");

        builder.setPositiveButton("Signup", (dialog, id) -> {
            EditText em = view.findViewById(R.id.textEmail);
            EditText password = view.findViewById(R.id.signupPassword);
            EditText confirmPassword = view.findViewById(R.id.confirmPasswordEditText);
            EditText address = view.findViewById(R.id.addressEdit);
            EditText cardNo = view.findViewById(R.id.creditEdit);

            confirmPassword.addTextChangedListener(textWatcher);

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


                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("ID", SIGNUP);
                intent.putExtra("SIGNUP_EMAIL", email);
                intent.putExtra("SIGNUP_PASSWORD", pwrd);
                intent.putExtra("SIGNUP_ADDR", addr);
                intent.putExtra("SIGNUP_CARD", card);
                startActivity(intent);

            } catch (NullPointerException npe) {
                npe.printStackTrace();
                userSignup(); // could re-display with some error message upon invalid login
            }

        });

        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // do nothing
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}