package com.example.spaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //handler class
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //intent
                Intent intent = new Intent(MainActivity.this, SelectionScreen.class);
                startActivity(intent);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    }
