package com.example.newapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newapp.R;

import java.util.ArrayList;

public class statusCustomAdapter extends ArrayAdapter<statusItem> {

    private String TAG  = "StatusAdapter";
    private ArrayList<statusItem> usersList;
    private Context context;

    public statusCustomAdapter(Context context, int textViewResourceId, ArrayList<statusItem> list) {
        super(context,textViewResourceId,list);
        usersList = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.status_view,null);

        ImageView imageView;
        TextView teacherName,avail;

        imageView = convertView.findViewById(R.id.imageView1);
        teacherName = convertView.findViewById(R.id.professorName1);
        avail = convertView.findViewById(R.id.status1);

       statusItem user= usersList.get(position);
        Glide.with(context).load(user.getImage()).into(imageView);
        teacherName.setText(user.getProfessorName());
        if(user.getStatus().equals("Free")){
            avail.setBackgroundResource(R.color.green);
        }
        else{
            avail.setBackgroundResource(R.color.red);
        }
        avail.setText(user.getStatus());

        return convertView;

    }

    public void updateList(ArrayList<statusItem> list ){
        usersList.clear();
        usersList.addAll(list);
        this.notifyDataSetChanged();
    }
}


