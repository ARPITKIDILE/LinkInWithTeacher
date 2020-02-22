package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dmax.dialog.SpotsDialog;
import static com.example.newapp.R.style.Custom;

public class Preview extends AppCompatActivity {

    private ImageView imageView;
    private Context context;
    AlertDialog waiting_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        waiting_dialog = new SpotsDialog.Builder()
                .setContext(Preview.this)
                .setMessage("...Please Wait...")
                .setTheme(Custom)
                .setCancelable(false)
                .build();
        waiting_dialog.show();
        imageView = findViewById(R.id.imageUrl);
        context = Preview.this;
        Bundle bundle = getIntent().getExtras();
        String imageUrl = bundle.getString("URL");

        //Toast.makeText(getApplicationContext(),"URL: "+imageUrl,Toast.LENGTH_LONG).show();
        Glide.with(context).load(imageUrl).into(imageView);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waiting_dialog.dismiss();

    }
}
