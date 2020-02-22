package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Switch aSwitch;
    ImageView button;
    TextView  availability;
    private DatabaseReference statusRef;
    private String uid="";
    private Boolean stat=false;
    private String currentStatus="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        loadItems();
        addListesners();




    }
    private void addListesners(){
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    onStatusChanged(b);
                }
            });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("UID",uid);
                intent.putExtra("Status",currentStatus);
                setResult(1,intent);
                finish();
            }
        });

        }

    void loadItems(){

        statusRef = FirebaseDatabase.getInstance().getReference("Users");
        aSwitch=(Switch)findViewById(R.id.switch1);
        availability=(TextView)findViewById(R.id.availability);
        button=(ImageView)findViewById(R.id.back);

        Bundle bundle = getIntent().getExtras();
        uid= bundle.getString("UID");
        currentStatus=bundle.getString("Status");
        if (currentStatus.equals("Free")){
            stat=true;
        }
        else{
            stat=false;
        }
        aSwitch.setChecked(stat);
        onStatusChanged(stat);


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent=new Intent();
        intent.putExtra("UID",uid);
        intent.putExtra("Status",currentStatus);
        setResult(1,intent);
        finish();

    }

    void onStatusChanged(Boolean b){

        String stat1="";
        if (b){
            availability.setTextColor(getResources().getColor(R.color.green));
            stat1="Free";
            statusRef.child(uid).child("avail").setValue(stat1);
        }
        else{
            availability.setTextColor(getResources().getColor(R.color.red));
            stat1="Busy";
            statusRef.child(uid).child("avail").setValue(stat1);

        }
        currentStatus=stat1;

    }
}
