package com.example.spaapp.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminDashboard extends AppCompatActivity {

    Button btnLogin;
    TextView textPass;
    TextInputEditText loginEmail, loginPass;
    String email, pass;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.login);
        loginPass = findViewById(R.id.textPassword);
        loginEmail = findViewById(R.id.textEmail);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    //validations
    private void loginUser() {
        email = loginEmail.getText().toString().trim();
        pass = loginPass.getText().toString().trim();

        if (!TextUtils.isEmpty(email)) {
            loginUserFirebase(email, pass);

        } else {
            Toast.makeText(this, "Email and Password fields have an issue", Toast.LENGTH_SHORT).show();
            SweetAlertDialog errorDialog = new SweetAlertDialog(AdminDashboard.this, SweetAlertDialog.ERROR_TYPE);
            errorDialog.setTitle("Email and Password Fields not correct");
            errorDialog.setCancelable(true);
            errorDialog.setCanceledOnTouchOutside(false);
            errorDialog.show();
        }


        if (!TextUtils.isEmpty(pass)) {
            loginUserFirebase(email, pass);

        } else {

            Toast.makeText(this, "Email and Password fields have an issue", Toast.LENGTH_SHORT).show();
            SweetAlertDialog errorDialog = new SweetAlertDialog(AdminDashboard.this, SweetAlertDialog.ERROR_TYPE);
            errorDialog.setTitle("Email and Password Fields not correct");
            errorDialog.setCancelable(true);
            errorDialog.setCanceledOnTouchOutside(false);
            errorDialog.show();
        }

    }

    private void loginUserFirebase(String email, String pass) {
        //giving user progress
        SweetAlertDialog progressDialog = new SweetAlertDialog(AdminDashboard.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.setTitleText("Signing in.. Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    SweetAlertDialog successDialog = new SweetAlertDialog(AdminDashboard.this, SweetAlertDialog.SUCCESS_TYPE);
                    successDialog.setTitleText("Account Verified" + task.isSuccessful());
                    successDialog.setCancelable(true);
                    successDialog.show();
                    updateUi();
                } else if (!task.isSuccessful()) {
                    progressDialog.dismiss();
                    SweetAlertDialog errorDialog = new SweetAlertDialog(AdminDashboard.this, SweetAlertDialog.ERROR_TYPE);
                    errorDialog.setTitleText("Error in signing in");
                    errorDialog.setCancelable(true);
                    errorDialog.setCanceledOnTouchOutside(false);
                    errorDialog.show();
                }
            }
        });
    }

    private void updateUi() {
        Intent intent = new Intent(AdminDashboard.this, AdminDash.class);
        startActivity(intent);
    }
}