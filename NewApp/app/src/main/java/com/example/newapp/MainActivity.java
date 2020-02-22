package com.example.newapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.newapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private ListView listView;
    private String uid, currentStatus,occupation;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;
    private FloatingActionButton fab;

    //datastructures

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadItems();
        Listeners();
    }


    void loadItems(){

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        fab = findViewById(R.id.fab);
        // Register the ListView  for Context menu
        registerForContextMenu(fab);


        Bundle bundle= (Bundle) getIntent().getExtras();

        occupation = bundle.getString("occupation");
        uid = bundle.getString("UID");
        currentStatus=bundle.getString("Status");


        //checking that its student or not
        if (occupation.equals("student"))fab.hide();


    }
    void Listeners(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(view);
            }
        });
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.uplaod_menu, menu);
        menu.setHeaderTitle("Select The Action");

    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.notice){
            startActivity(new Intent(getApplicationContext(),NoticeActivity.class));
        }
        else if(item.getItemId()==R.id.status){

            startActivityForResult(new Intent(getApplicationContext(),StatusActivity.class).putExtra("UID",uid).putExtra("Status",currentStatus),1);

        }else{
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            //Toast.makeText(getApplicationContext(),"Hello " +currentStatus,Toast.LENGTH_LONG).show();
                uid = data.getStringExtra("UID");
            currentStatus = data.getStringExtra("Status");
        }

    }


}