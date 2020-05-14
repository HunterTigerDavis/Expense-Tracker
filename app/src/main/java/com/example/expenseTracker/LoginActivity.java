package com.example.expenseTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.*;
import java.util.prefs.Preferences;

public class LoginActivity extends AppCompatActivity {

    // Dictionary of users here:
    //public static HashMap<String, String> users = new HashMap();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Storing user login data through SharedPreferences
        setDefaults("user", "pass", getApplicationContext());

        //users.put("user", "pass"); // default login
        //System.out.println("User" + users.size());

        // Check if the user is already logged in, if so we skip the login screen and go to welcome
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())){
            Intent welcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(welcomeIntent);
        }

    }

    // Write to SharedPreferences
    public static void setDefaults(String key, String value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // Read from SharedPreferences
    public static String getDefaults(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public void logIn(View view){
        EditText user = findViewById(R.id.usernameField);
        EditText pass = findViewById(R.id.passwordField);
        String username = user.getText().toString();
        String password = pass.getText().toString();

        // Username/password completion validation
        if (password.length() < 1 && username.length() < 1){
            Toast completeToast = Toast.makeText(getApplicationContext(), "Please complete all fields", Toast.LENGTH_LONG);
            completeToast.show();
        }
        else{ // If they complete both fields, validate with dictionary
            // If user doesn't exist, password will be null
            String passResult = getDefaults(username, getApplicationContext());
            System.out.println("TEST USERNAME: " + passResult);
            if (!passResult.equals(null) && password.equals(passResult)){
                // We pass
                // Set user to logged in
                SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
                welcomeIntent.putExtra("USERNAME", username);
                welcomeIntent.putExtra("PASSWORD", password);
                startActivity(welcomeIntent);
            }
            else{
                // We fail the login
                Toast invalidToast = Toast.makeText(getApplicationContext(), "Invalid login credentials", Toast.LENGTH_LONG);
                invalidToast.show();
                pass.setText(null);
            }

        }

    }

    public void openSignUp(View view){
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        //signUpIntent.putExtra("users", this.users);
        startActivity(signUpIntent);
    }

}
