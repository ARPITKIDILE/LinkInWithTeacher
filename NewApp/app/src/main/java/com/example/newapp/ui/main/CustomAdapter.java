package com.example.newapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.newapp.Preview;
import com.example.newapp.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<NoticeItem>{
    Context context;
    int resource;
    ArrayList<NoticeItem> items;
    NoticeItem item;
    customButtonListener customListener;



    ////adding custom listeners
    public interface customButtonListener{
        public void onAttachmentClickListener(int position);

    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListener = listener;
    }

    ////adapter functions

    public CustomAdapter(Context context, int resource, ArrayList<NoticeItem> items) {
        super(context, resource,items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {


        //inflating the layout of product
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.notice_view,null);
        ViewHolder viewHolder=new ViewHolder();
        viewHolder.title = (TextView) view.findViewById(R.id.title1);
        viewHolder.content = (TextView) view.findViewById(R.id.content1);
        viewHolder.attachment=(Button)view.findViewById(R.id.download1);
        item = items.get(position);

        //setting it
        viewHolder.content.setText(item.getBody());
        viewHolder.title.setText(item.getTitle());

        viewHolder.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), Preview.class);
                item=items.get(position);
                intent.putExtra("URL",item.getImageUrl());
                context.startActivity(intent);
            }
        });
        return view;
    }

    public void updateNotice(ArrayList<NoticeItem> list){
        items.clear();
        items.addAll(list);
        this.notifyDataSetChanged();
    }



    public class ViewHolder {
        TextView content ;
        TextView title;
        Button attachment;
    }

}
