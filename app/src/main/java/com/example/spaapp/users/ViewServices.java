package com.example.spaapp.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.spaapp.R;

import java.util.ArrayList;

public class ViewServices extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        ArrayList<ViewServicesModel> exampleList = new ArrayList<>();
        exampleList.add(new ViewServicesModel(R.drawable.fullpack, "Full Package: This package includes massages , make up applications , manicures and pedicures"));
        exampleList.add(new ViewServicesModel(R.drawable.massagespa, "Massage: The two most popular massages are Swedish massage and deep tissue massage. Swedish massage typically covers the whole body with firm strokes but without much deep, focused work. Deep tissue massage will use firmer pressure and also include focused work on areas that are especially tight."));
        exampleList.add(new ViewServicesModel(R.drawable.facials, "Facials: A facial is essentially a multi-step skin treatment that is one of the best ways to take care of your skin. A facial cleanses, exfoliates, and nourishes the skin, promoting a clear, well-hydrated complexion and can help your skin look younger. You also receive advice on the best way to take care of your skin. A facial works best when it is part of an on-going program of skin care. "));
        exampleList.add(new ViewServicesModel(R.drawable.manicure, "Manicure: A manicure is a cosmetic beauty treatment for the fingernails and hands performed at home or in a nail salon. A manicure consists of filing and shaping the free edge of nails, pushing and clipping any nonliving tissue, treatments with various liquids, massage of the hand, and the application of fingernail polish."));
        exampleList.add(new ViewServicesModel(R.drawable.pedicure, "Pedicure: A pedicure is a cosmetic treatment of the feet and toenails, analogous to a manicure. Pedicures are done for cosmetic, therapeutic purposes. "));

        mRecyclerView = findViewById(R.id.recyclerServices);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ViewServicesAdapter(this,exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}