package com.example.expenseTracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler; // for splash screen

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    // For splash screen:
    private static int splash_time_out = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, splash_time_out);

    }
}
