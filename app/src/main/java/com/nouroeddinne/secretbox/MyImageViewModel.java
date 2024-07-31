package com.nouroeddinne.secretbox;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyImageViewModel extends AndroidViewModel {

    private  MyImagesRespostry mymageRespostry;
    private MyImages image;

    public MyImageViewModel(@NonNull Application application) {
        super(application);
        mymageRespostry = new MyImagesRespostry(application);
    }

    public void incert(MyImages myImages){
        mymageRespostry.incert(myImages);
    }

    public void delete(MyImages myImages){
        mymageRespostry.delete(myImages);
    }

    public void deleteImage(int id){
        mymageRespostry.deleteImage(id);
    }

    public void update(MyImages myImages){
        mymageRespostry.update(myImages);
    }

    public LiveData<List<MyImages>> getAllImages(){
        return mymageRespostry.getAllImages();
    }

    public LiveData<MyImages> getImageById(int id){
        return mymageRespostry.getImageById(id);
    }








}
