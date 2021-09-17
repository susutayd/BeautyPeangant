package com.example.spaapp.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spaapp.R;
import com.example.spaapp.dashboards.AdminDash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ConBookingsAdminAdapter extends RecyclerView.Adapter<ConBookingsAdminAdapter.ConViewHolder> {
    Context context;
    List<BookingsModel> bookModels;

    //matching constructor
    public ConBookingsAdminAdapter(Context context , List<BookingsModel> bookingsModels){
        this.context = context;
        this.bookModels = bookingsModels;
    }
    @NonNull
    @Override
    public ConBookingsAdminAdapter.ConViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conbook_item, parent, false);
        ConBookingsAdminAdapter.ConViewHolder conViewHolder = new ConBookingsAdminAdapter.ConViewHolder(v); //new instance
        return conViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConBookingsAdminAdapter.ConViewHolder holder, int position) {
        BookingsModel positionBook = bookModels.get(position);

        holder.clientBook.setText(positionBook.getClient_name());
        holder.clientPhone.setText(positionBook.getClient_phone());
        holder.clientService.setText(positionBook.getClient_service());
        holder.clientTime.setText(positionBook.getClient_time());
        holder.clientDate.setText(positionBook.getClient_date());

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BookingsAdmin.class);
                context.startActivity(intent);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ConfirmedBookings").child(positionBook.getId());
                databaseReference.removeValue();
                Toast.makeText(context, "Service confirmed", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }


    public static class ConViewHolder extends RecyclerView.ViewHolder{
        TextView clientBook,clientPhone,clientService,clientTime,clientDate;
        Button btnConfirm,btnReject;

        public ConViewHolder(@NonNull View itemView) {
            super(itemView);

            clientBook = itemView.findViewById(R.id.clientBook);
            clientPhone = itemView.findViewById(R.id.phoneBook);
            clientService = itemView.findViewById(R.id.serviceBook);
            clientTime = itemView.findViewById(R.id.timeBook);
            clientDate = itemView.findViewById(R.id.datebook);
            btnConfirm = itemView.findViewById(R.id.confirmService);

        }

    }

}


