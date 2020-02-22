package com.example.newapp.ui.main;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Product implements Parcelable {
    private String name;
    private String price;
    private Integer image;
    private Integer quantity;

    public Product( String name, String price, Integer image) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity=0;
    }


    protected Product(Parcel in) {
        name = in.readString();
        price = in.readString();
        image= in.readInt();
        quantity=in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public Integer getImage() {
        return image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeInt(image);
        parcel.writeInt(quantity);
    }
}

