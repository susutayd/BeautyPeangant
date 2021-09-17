package com.example.spaapp.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.spaapp.R;
import com.example.spaapp.admin.BookingsModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookServices extends AppCompatActivity  implements
        AdapterView.OnItemSelectedListener {
    String[] services = { "Full Package", "Massage", "Pedicure", "Manicure", "Facials"};
    TextInputEditText edittext,edittext3,edittext4;
    TextInputEditText edittext2;
    Calendar myCalendar;
    String clientName,clientPhones,clientServices,clientTime,clientDate;
    DatabaseReference databaseReference;
    Button submitBooking;
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_services);


        databaseReference = FirebaseDatabase.getInstance().getReference("spaBookings");

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
         spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,services);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        myCalendar = Calendar.getInstance();

         edittext=  findViewById(R.id.date);
         edittext2=  findViewById(R.id.time);
         edittext3 = findViewById(R.id.fullName);
         edittext4 = findViewById(R.id.phone);

        submitBooking = findViewById(R.id.submitBookinga);

        submitBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingSubmission();
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BookServices.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edittext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookServices.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edittext2.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(),services[position] , Toast.LENGTH_LONG).show();
        clientServices = services[position].toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void bookingSubmission() {
        clientName = edittext3.getText().toString().trim();
        clientPhones = String.valueOf(edittext4.getText());
        clientTime = edittext2.getText().toString().trim();
        clientDate = edittext.getText().toString().trim();

        //create an id for unique identification of records
        String uploadID = databaseReference.push().getKey();
        //pushing data to model class
        BookingsModel bookingsModel = new BookingsModel(uploadID,clientName,clientPhones,clientServices,clientTime,clientDate);
        //execute the model to take data to firebase
        databaseReference.child(uploadID).setValue(bookingsModel);
        SweetAlertDialog success = new SweetAlertDialog(BookServices.this,SweetAlertDialog.SUCCESS_TYPE);
        success.setTitle("Booking Recorded: You be called by our team with details to confirm your booking. This usually takes 1 to 20 minutes.");
        success.setCancelable(true);
        success.setCanceledOnTouchOutside(false);
        success.show();
        //clear text
        edittext.setText("");
        edittext2.setText("");
        edittext3.setText("");
        edittext4.setText("");

    }
}