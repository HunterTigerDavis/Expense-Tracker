package com.example.expenseTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

/**
 * This Activity is where the app goes after the login page, or after the splash screen
 * if they're already logged in. It contains the fragment to display the graphs,
 * and is scrollable smoothly. It contains the button for log out and settings.
 * This screen is where the users input their income, expenses, and savings.
 */
public class WelcomeActivity extends AppCompatActivity {

    private Button calculateButton;
    private EditText incomeText;
    private EditText expenseText;
    private EditText savingsText;
    public static String income, expenses, savings;
    private String[] labels;
    private androidx.appcompat.widget.Toolbar toolbar;
    public static String username, password;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // TODO: Add $ sign in front of user input
        incomeText = findViewById(R.id.incomeField);
        // Dropdown for monthly, annual income (OPTIONAL)
        //Spinner incomeSpinner = findViewById(R.id.incomeSpinner);
        expenseText = findViewById(R.id.expenseField);
        savingsText = findViewById(R.id.savingsField);

        calculateButton = findViewById(R.id.calculateButton);

        // When button is pressed, calculate
        calculateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                calculateButtonPressed();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.settings_button:
                // Open settings menu
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
//                settingsIntent.putExtra("USERNAME", username);
//                settingsIntent.putExtra("PASSWORD", password);
                startActivity(settingsIntent);
                return true;
            case R.id.logout_button:
                // Log the user out and kick to login screen
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                return true;
        }
        return false;
    }


    // This is called when the "Calculate" button is pressed, generates the graphs with current inputs
    private void calculateButtonPressed(){
        income = incomeText.getText().toString().trim();
        expenses = expenseText.getText().toString().trim();
        savings = savingsText.getText().toString().trim();

        if (TextUtils.isEmpty(income)){
            incomeText.setError("All Fields Are Required.");
            return;
        }
        if (TextUtils.isEmpty(expenses)) {
            expenseText.setError("All Fields Are Required.");
            return;
        }
        if (TextUtils.isEmpty(savings)){
            savingsText.setError("All Fields Are Required.");
            return;
        }
        System.out.println("INCOME: " + income);
        System.out.println("EXPENSES: " + expenses);
        System.out.println("SAVINGS: " + savings);

        // Open a new fragment for the display
        if (findViewById(R.id.fragmentContainer) != null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,
                    new HomeFragment(), null).addToBackStack(null).commit();
        }
    }
}


