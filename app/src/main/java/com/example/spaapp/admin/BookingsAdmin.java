package com.example.spaapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.spaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookingsAdmin extends AppCompatActivity {
    private RecyclerView recyclerView;
    //adapter
    BookingsAdapter bookingsAdapter;
    //model
    List<BookingsModel> bookingsModels;
    //database Ref
    private DatabaseReference databaseReference;
    //progress bar
    ProgressBar progressBar;
    //Query
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_admin);


        //instance of our database ref
        databaseReference = FirebaseDatabase.getInstance().getReference("spaBookings");

        //progress bar
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerBook);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);

        bookingsModels = new ArrayList<>();
        bookingsAdapter = new BookingsAdapter(BookingsAdmin.this,bookingsModels);
        recyclerView.setAdapter(bookingsAdapter);

        //reading data from firebase
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //for loop to iterate the datasnapshot
                bookingsModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //getting values and sending them to my model class

                    BookingsModel shoeModel = dataSnapshot.getValue(BookingsModel.class);
                    Log.d("values " , "values from firebase " + dataSnapshot);
                    //add new records fetched
                    bookingsModels.add(shoeModel);
                }

                bookingsAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                SweetAlertDialog errorDialog = new SweetAlertDialog(BookingsAdmin.this,SweetAlertDialog.ERROR_TYPE);
                errorDialog.setTitle("Something went wrong");
                errorDialog.setTitleText(String.valueOf(error));
                errorDialog.setCancelable(true);
                errorDialog.setCanceledOnTouchOutside(true);
                errorDialog.show();
                progressBar.setVisibility(View.GONE);
            }
        });



    }
}