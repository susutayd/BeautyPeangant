package com.example.spaapp.admin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spaapp.R;
import com.example.spaapp.users.BookServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookViewHolder> {
    Context context;
    List<BookingsModel> bookModels;
    public static int MY_PERMISSIONS_REQUEST_CALL_PHONE = 5555;

    //matching constructor
    public BookingsAdapter(Context context , List<BookingsModel> bookingsModels){
        this.context = context;
        this.bookModels = bookingsModels;
    }
    @NonNull
    @Override
    public BookingsAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        BookingsAdapter.BookViewHolder bookViewHolder = new BookingsAdapter.BookViewHolder(v); //new instance
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsAdapter.BookViewHolder holder, int position) {
          BookingsModel positionBook = bookModels.get(position);

          holder.clientBook.setText(positionBook.getClient_name());
          holder.clientPhone.setText(positionBook.getClient_phone());
          holder.clientPhone.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + positionBook.getClient_phone()));
                  context.startActivity(intent);
              }
          });
          holder.clientService.setText(positionBook.getClient_service());
          holder.clientTime.setText(positionBook.getClient_time());
          holder.clientDate.setText(positionBook.getClient_date());

          holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //create an id for unique identification of records
                  DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ConfirmedBookings");
                  String uploadID = databaseReference1.push().getKey();
                  String clientName = positionBook.getClient_name();
                  String clientPhones = positionBook.getClient_phone();
                  String clientServices = positionBook.getClient_service();
                  String clientTime = positionBook.getClient_time();
                  String clientDate = positionBook.getClient_date();
                  //pushing data to model class
                  BookingsModel bookingsModel = new BookingsModel(uploadID,clientName,clientPhones,clientServices,clientTime,clientDate);
                  //execute the model to take data to firebase
                  databaseReference1.child(uploadID).setValue(bookingsModel);
                  SweetAlertDialog success = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
                  success.setTitle("Booking Confirmed");
                  success.setCancelable(true);
                  success.setCanceledOnTouchOutside(false);
                  success.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                      @Override
                      public void onClick(SweetAlertDialog sweetAlertDialog) {
                          Intent updateui = new Intent(context,ConBookingsAdmin.class);
                          context.startActivity(updateui);
                          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("spaBookings").child(positionBook.getId());
                          databaseReference.removeValue();
                          success.dismiss();

                      }
                  });
                  success.show();

              }
          });

          holder.btnReview.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //raise alert to give admin instructions
                  AlertDialog.Builder builder = new AlertDialog.Builder(context);
                  builder.setTitle("Reviewing Booking:");
                  builder.setMessage(" Call client to change date/time of this booking , if the client agrees to change the booking date proceed to remove the Booking.");
                  builder.setPositiveButton("Call Client",
                          new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                  String number = ("tel:" + positionBook.getClient_phone());
                                  Intent mIntent = new Intent(Intent.ACTION_CALL);
                                  mIntent.setData(Uri.parse(number));
// Here, thisActivity is the current activity
                                  if (ContextCompat.checkSelfPermission(context,
                                          Manifest.permission.CALL_PHONE)
                                          != PackageManager.PERMISSION_GRANTED) {

                                      ActivityCompat.requestPermissions((Activity) context,
                                              new String[]{Manifest.permission.CALL_PHONE},
                                              MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                      // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                      // app-defined int constant. The callback method gets the
                                      // result of the request.
                                  } else {
                                      //You already have permission
                                      try {
                                          context.startActivity(mIntent);
                                      } catch(SecurityException e) {
                                          e.printStackTrace();
                                      }
                                  }
                              }
                          });
                  builder.setNegativeButton("Remove Booking", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("spaBookings").child(positionBook.getId());
                          databaseReference.removeValue();
                      }
                  });

                  builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                      }
                  });
                  builder.setCancelable(false);
                  builder.show();
              }
          });


    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder{
         TextView clientBook,clientPhone,clientService,clientTime,clientDate;
         Button btnConfirm,btnReview,btnReject;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            clientBook = itemView.findViewById(R.id.clientBook);
            clientPhone = itemView.findViewById(R.id.phoneBook);
            clientService = itemView.findViewById(R.id.serviceBook);
            clientTime = itemView.findViewById(R.id.timeBook);
            clientDate = itemView.findViewById(R.id.datebook);
            btnConfirm = itemView.findViewById(R.id.confirmBooking);
            btnReview = itemView.findViewById(R.id.reviewBooking);
        }

    }

    }

