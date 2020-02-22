package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

import static com.example.newapp.R.style.Custom;

public class Register extends AppCompatActivity {

    private EditText mail,password;
    private Button register;
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private AlertDialog waiting_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        waiting_dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("...Please Wait...")
                .setTheme(Custom)
                .setCancelable(false)
                .build();
        Views();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waiting_dialog.show();
                registration();
            }
        });
    }

    void Views(){
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        context = Register.this;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    void registration(){
        final String email  = mail.getText().toString();
        final String pass = password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Email field cannot be empty", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(context, "Password field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Users user = null;
                        user = new Users(email,pass,"student");
                        databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
                                waiting_dialog.dismiss();
                                startActivity(new Intent(Register.this,Login.class));

                            } else {
                                Toast.makeText(context, "Registraion failed", Toast.LENGTH_SHORT).show();
                                waiting_dialog.dismiss();
                            }
                        }
                    });
                } else {
                    waiting_dialog.dismiss();
                    Toast.makeText(context, "Already Registration ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
