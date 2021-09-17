package com.example.spaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.spaapp.dashboards.AdminDashboard;
import com.example.spaapp.dashboards.UserDashboard;

public class SelectionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
    }

    public void bookAppoint(View v){
        Intent intent = new Intent(this, UserDashboard.class);
        startActivity(intent);
    }

    public void spaAdmin(View v){
        Intent intent = new Intent(this, AdminDashboard.class);
        startActivity(intent);
    }
}