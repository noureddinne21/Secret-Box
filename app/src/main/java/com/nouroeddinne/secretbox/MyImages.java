package com.nouroeddinne.secretbox;

import android.os.Parcel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "myImages")
public class MyImages{

    @PrimaryKey(autoGenerate = true)
    private int image_id;
    private String image_titel;
    private String image_description;
    //private byte [] image;
    private String image;

    public MyImages(int image_id, String image_titel, String image_description, String image) {
        this.image_id = image_id;
        this.image_titel = image_titel;
        this.image_description = image_description;
        this.image = image;
    }

    public MyImages(String image_titel, String image_description, String image) {
        this.image_titel = image_titel;
        this.image_description = image_description;
        this.image = image;
    }

    public MyImages() {
    }

    protected MyImages(Parcel in) {
        image_id = in.readInt();
        image_titel = in.readString();
        image_description = in.readString();
        image = in.readString();
    }


    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage_titel() {
        return image_titel;
    }

    public void setImage_titel(String image_titel) {
        this.image_titel = image_titel;
    }

    public String getImage_description() {
        return image_description;
    }

    public void setImage_description(String image_description) {
        this.image_description = image_description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
































}
