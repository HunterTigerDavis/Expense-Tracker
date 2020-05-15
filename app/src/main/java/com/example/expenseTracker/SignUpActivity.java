package com.example.expenseTracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

/**
 * This Activity is where the user can create a new account with username and password
 */
public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "Sign Up";
    private Button signUpButton;
    private EditText userName;
    private EditText userPassword;
    private EditText userPassword0;
    private EditText userEmail;
    private EditText userCellPhone;
    //private HashMap<String, String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //assigning elements to variables
        signUpButton = findViewById(R.id.signUpButton);
        userName = findViewById(R.id.usernameField);
        userPassword = findViewById(R.id.passwordField);
        userPassword0 = findViewById(R.id.passwordField2);
        userEmail = findViewById(R.id.emailField);
        userCellPhone = findViewById(R.id.phoneField);
        Intent intent = getIntent();
        //users = (HashMap<String, String>)intent.getSerializableExtra("users");
        //Log.w(TAG, "users: " + users);
        //----------Setting up listener for sign up button, collect input----------------
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userNameInput = userName.getText().toString();
                String passwordInput = userPassword.getText().toString();
                String passwordInput0 = userPassword0.getText().toString();
                String emailInput = userEmail.getText().toString();
                String cellPhoneInput = userCellPhone.getText().toString();

                //------------call sign up method--------------
                attemptSignUp(userNameInput, passwordInput, passwordInput0, emailInput, cellPhoneInput);
            }
        });
    }

    //------------Sign up logic---------------------
    private void attemptSignUp(String userNameInput, String passwordInput,String passwordInput0,String emailInput,String cellPhoneInput) {
        //---------validate form-------------------
        if(!validateAllInputs(userNameInput, passwordInput, passwordInput0, emailInput, cellPhoneInput)) { return; }
        else {
            // Enter the user into our shared preferences if it passes
            LoginActivity.setDefaults(userNameInput, passwordInput, getApplicationContext());


            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    //-------------Project validation requirements--------------------------
    private boolean validateAllInputs(String userNameInput, String passwordInput,String passwordInput0,String emailInput,String cellPhoneInput) {
        // Retrieve the SharedPreferences
        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userResult = LoginActivity.getDefaults(userNameInput, getApplicationContext());
        boolean isValid = true;
        //-----------Java util library for regex--------------
        //-----------Email REGEX---------------
        String regexForEmails = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern patternForEmail = Pattern.compile(regexForEmails);
        Matcher matcherForEmail = patternForEmail.matcher(emailInput);
        //-----------cell phone U.S. REGEX---------------
        String regexForCell = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern patternForCell = Pattern.compile(regexForCell);
        Matcher matcherForCell = patternForCell.matcher(cellPhoneInput);
        Log.w(TAG, "Checking regex for email " + matcherForEmail.matches());
        Log.w(TAG, "Checking regex for cell " + matcherForCell.matches());
        Log.w(TAG, "Does user already exist? (Null if false)" + userResult);

        //------------Assignment 1 requirements, 2bi. All fields must be filled----------------
        if (TextUtils.isEmpty(userNameInput)){
            userName.setError("All Fields Are Required.");
            isValid = false;
        }if(TextUtils.isEmpty(passwordInput)){
            userPassword.setError("All Fields Are Required.");
            isValid = false;
        }if(TextUtils.isEmpty(passwordInput0)){
            userPassword0.setError("All Fields Are Required.");
            isValid = false;
        }if(TextUtils.isEmpty(emailInput)){
            userEmail.setError("All Fields Are Required.");
            isValid = false;
        }if(TextUtils.isEmpty(cellPhoneInput)){
            userCellPhone.setError("All Fields Are Required.");
            isValid = false;

            //-----------Assignment 1 requirements, 2biii. Password and retype password must be the same----------------
        }if(passwordInput.compareTo(passwordInput0) !=0) {
            userPassword.setError("Passwords must match");
            isValid = false;
            //-----------Assignment 1 requirements, 2biv. email must be in correct format----------------
        }if(!matcherForEmail.matches()) {
            userEmail.setError("Email Must be in correct format.");
            isValid = false;
            //-----------Assignment 1 requirements, 2biv. phone must be in correct format----------------
        }if(!matcherForCell.matches()) {
            userCellPhone.setError("Cell Must be in correct format.");
            isValid = false;
            //-----------Assignment 1 requirements, 2bii. username must be in correct format----------------
        // If the username already exists, they cannot sign up with the same one
        }if(userResult != null) {
            userName.setError("Username already exists.");
            isValid = false;
        }
        return isValid;
    }
}