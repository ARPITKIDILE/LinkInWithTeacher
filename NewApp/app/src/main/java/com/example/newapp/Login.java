package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

import static com.example.newapp.R.style.Custom;


public class Login extends AppCompatActivity {

    private EditText email,password;
    Button login;
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    AlertDialog waiting_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        waiting_dialog = new SpotsDialog.Builder()
                .setContext(Login.this)
                .setMessage("...Please Wait...")
                .setTheme(Custom)
                .setCancelable(false)
                .build();
        loadViews();
        listeners();
    }

    void loadViews(){

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        context=Login.this;
        mAuth = FirebaseAuth.getInstance();
        databaseReference  = FirebaseDatabase.getInstance().getReference("Users");

    }

    void listeners(){


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waiting_dialog.show();
                signIn();

            }
        });

    }


    private void signIn() {

        String email = this.email.getText().toString();
        String pass = password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Email field cannot be empty", Toast.LENGTH_SHORT).show();
            waiting_dialog.dismiss();
        }


        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(context, "Password field cannot be empty", Toast.LENGTH_SHORT).show();
            waiting_dialog.dismiss();
        }
        else {

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show();
                        waiting_dialog.dismiss();
                        return;
                    }

                    final String[] strArr = {""};

                    databaseReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }

                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            strArr[0] = dataSnapshot.child("occupation").getValue().toString();
                            String currentStatus="",uid="";

                            if (strArr[0].equalsIgnoreCase("teacher")) {
                                //progressDialog.show();
                                currentStatus = dataSnapshot.child("avail").getValue().toString();
                                Toast.makeText(context, currentStatus, Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "Welcome Teacher ", Toast.LENGTH_SHORT).show();
                                uid = dataSnapshot.getKey().toString();
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("occupation", "teacher");
                                intent.putExtra("UID",uid);
                                intent.putExtra("Status",currentStatus);
                                waiting_dialog.dismiss();
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(context, "Welcome student", Toast.LENGTH_SHORT).show();
                                waiting_dialog.dismiss();
                                startActivity(new Intent(context, MainActivity.class).putExtra("occupation", "student").putExtra("UID", uid).putExtra("Status", currentStatus));
                            }
                        }
                    });
                }
            });
        }

}





}
