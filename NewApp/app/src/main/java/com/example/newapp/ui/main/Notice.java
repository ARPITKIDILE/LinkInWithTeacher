package com.example.newapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newapp.Preview;
import com.example.newapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notice extends Fragment{

    private ListView listView;
    private ArrayList<NoticeItem> items;
    private CustomAdapter adapter;
    private DatabaseReference databaseReference;
    NoticeItem item;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.notice, container, false);
        items=new ArrayList<>();
        loadingItems();
        settingAdapter(root);
        return root;
    }
    void loadingItems(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Notices");
        setNotices();
    }
    void settingAdapter(View root) {

        adapter = new CustomAdapter(getActivity(), R.layout.notice_view, items);
        listView = (ListView) root.findViewById(R.id.listView);
        //adapter.setCustomButtonListner(this);
        listView.setAdapter(adapter);
    }

    void setNotices(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items = new ArrayList<>();
                String image, title, body;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    image = data.child("imageUrl").getValue().toString();
                    title = data.child("title").getValue().toString();
                    body = data.child("body").getValue().toString();
                    NoticeItem item = new NoticeItem(title,body,image);
                    items.add(item);
                }
                adapter.updateNotice(items);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /*
    @Override
    public void onAttachmentClickListener(int position) {

        Toast.makeText(getContext(),"Aa toh gya ",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(getContext(), Preview.class);
                    item=items.get(position);
                    intent.putExtra("URL",item.getImageUrl());
                    startActivity(intent);
    }
    */
}
