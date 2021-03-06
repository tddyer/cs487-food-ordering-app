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

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UNCOMMENT THE LINE BELOW IF YOU WANT TO REFRESH THE APPS DATABASE TO DEFAULT (no active orders, 3 test users, 3 staff members)
//        this.deleteDatabase("OrderQuikDB");

        databaseHandler = new DatabaseHandler(this);


        /* ----------- USERS + STAFF DATABASE TEST DATA CREATION + RETRIEVAL TESTING ----------- */


//        // adding test users to database
        User u1 = new User("jsmith@gmail.com", "abc123", "3302 Yellow Rd, New York, NY, 10330", "2287349059230087", 200, "11/17/20 - Cheeseburger, Salad, Gyros");
        User u2 = new User("loganb22@gmail.com", "loganb123", "441 State St, Boston, MA, 33409", "7763409867113643", 80, "10/22/20 - Beef and Broccoli Stir-Fry, Salad");
        User u3 = new User("sashayarns@yahoo.com", "kittengirl!", "97431 Bluebird Ln, Austin, TX, 55609", "1103448967204789", 0, "");

        databaseHandler.addUser(u1);
        databaseHandler.addUser(u2);
        databaseHandler.addUser(u3);
//
//        // fetching user data test
//        User temp = databaseHandler.loadUser("jsmith@gmail.com", "abc123");
//        Log.d("LOGINACTIVITY", "onCreate: " + temp.getDeliveryAddress());
//
        // adding test staff members to database
        StaffMember s1 = new StaffMember(1111, "abc123");
        StaffMember s2 = new StaffMember(1112, "funny01");
        StaffMember s3 = new StaffMember(1113, "johnnyb101");

        databaseHandler.addStaff(s1);
        databaseHandler.addStaff(s2);
        databaseHandler.addStaff(s3);
//
//        // fetching staff data test
//        StaffMember tempStaff = databaseHandler.loadStaff(1111, "abc123");
//        Log.d("TAG", "onCreate: " + tempStaff.getStaffID() + ", " + tempStaff.getPassword());
    }


    @SuppressLint("NonConstantResourceId")
    public void login(View v) {
        switch (v.getId()) {
            case R.id.userLogin: // launch main activity with login_id as entered email and password
                // account login
                userLogin(false);
                break;
            case R.id.guestLogin: // launch main activity without login info
                guestLogin();
                break;
            case R.id.staffLogin: // launch staff portal with valid staff credentials
                // staff login
                staffLogin(false);
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

    public void userLogin(boolean invalid) {

        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.user_login_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("User Login");

        EditText em = view.findViewById(R.id.textEmail);
        EditText password = view.findViewById(R.id.textPassword);

        if (invalid) {
            em.setError("There is no registered account for the provided email/password. Please try again.");
        }

        builder.setPositiveButton("Login", (dialog, id) -> {
            String email;
            String pwrd;

            try {
                email = String.valueOf(em.getText());
                pwrd = String.valueOf(password.getText());

                User user = databaseHandler.loadUser(email, pwrd);

                if (user.getEmail().equals(email)) {

                    // successful login
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("USER_DATA", user);
                    startActivity(intent);
                } else {
                    userLogin(true);
                }

            } catch (NullPointerException npe) {
                npe.printStackTrace();
                userLogin(true); // could re-display with some error message upon invalid login
            }

        });

        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // do nothing
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void staffLogin(boolean invalid) {

        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.staff_login_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Staff Login");

        EditText em = view.findViewById(R.id.textID);
        EditText password = view.findViewById(R.id.textPassword);

        if (invalid) {
            em.setError("Invalid staff login credentials.");
        }

        builder.setPositiveButton("Login", (dialog, id) -> {

            int staffID = -1;
            String pwrd;

            try {
                if (em.getText().toString().length() > 0)
                    staffID = Integer.parseInt(em.getText().toString());

                pwrd = String.valueOf(password.getText());

                StaffMember staffMember = databaseHandler.loadStaff(staffID, pwrd);

                // successful login
                if (staffMember.getStaffID() > -1) {
                    Intent intent = new Intent(this, StaffPortal.class);
                    intent.putExtra("LOGIN_ID", staffID);
                    intent.putExtra("LOGIN_PASSWORD", pwrd);
                    startActivity(intent);
                } else {
                    staffLogin(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
                staffLogin(true);
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

        if (invalid)
            confirmPassword.setError("Error: passwords must match. Please try again.");


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
                    // creating user account + saving to db
                    User u = new User(email, pwrd, addr, card, 0, "");
                    databaseHandler.addUser(u);

                    // logging in with entered user info
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USER_DATA", u);
                    LoginActivity.this.startActivity(intent);
                } else {
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