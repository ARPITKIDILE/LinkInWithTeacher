package com.example.newapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newapp.R;
import com.example.newapp.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Status extends Fragment {

    private ListView listView1;
    private ArrayList<statusItem> items;
    private statusCustomAdapter adapter;

    private DatabaseReference databaseReference;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.status, container, false);
        items = new ArrayList<>();
        settingAdapter(root);
        loadingItems();
        return root;
    }

    void loadingItems() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        setStatus();
        //Take the data

    }

    void settingAdapter(View root) {

        adapter = new statusCustomAdapter(getActivity(), R.layout.status_view, items);
        listView1 = (ListView) root.findViewById(R.id.listView);
        //adapter.setCustomButtonListner(this);
        listView1.setAdapter(adapter);
    }


    private void setStatus() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String email = "", password = "", occupation = "", imagesrc = "", avail = "", name = "";

                    occupation = data.child("occupation").getValue().toString();

                    if (occupation.equals("teacher")) {

                        name = data.child("name").getValue().toString();
                        email = data.child("email").getValue().toString();
                        password = data.child("password").getValue().toString();
                        imagesrc = data.child("imagesrc").getValue().toString();
                        avail = data.child("avail").getValue().toString();

                        //Toast.makeText(getContext(), "Email = " + email + "Avail = " + avail, Toast.LENGTH_LONG).show();

                        statusItem user = new statusItem(name,avail,imagesrc,email,password,occupation);

                        items.add(user);

                    }
                }
                    adapter.updateList(items);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
