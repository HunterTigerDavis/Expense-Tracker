package com.example.expenseTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private Button calculateButton;
    private EditText incomeText;
    private EditText expenseText;
    private EditText savingsText;
    public static String income, expenses, savings;
    private String[] labels;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Resources res = getResources();
        labels = res.getStringArray(R.array.incomeEntryOptions);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

//        TextView usernameDisplay = findViewById(R.id.usernameDisplay);
//        usernameDisplay.setText("Welcome " + username + "!");

        setTitle("Welcome " + username + "!");

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

    private void calculateButtonPressed(){
        income = incomeText.getText().toString().trim();
        expenses = expenseText.getText().toString().trim();
        savings = savingsText.getText().toString().trim();

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


