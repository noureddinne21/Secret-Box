package com.nouroeddinne.secretbox;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyImagesRespostry {

    private MyImagesDao myImagesDao;

    public MyImagesRespostry(Application application){
        MyImagesDatabase database = MyImagesDatabase.getInestens(application);
        myImagesDao= database.myImagesDao();
    }

    public void incert(MyImages myImages){
        MyImagesDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                myImagesDao.insert(myImages);
            }
        });
    }

    public void update(MyImages myImages){
        MyImagesDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                myImagesDao.update(myImages);
            }
        });
    }

    public void delete(MyImages myImages){
        MyImagesDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                myImagesDao.delete(myImages);
            }
        });
    }

    public void deleteImage(int id){
        MyImagesDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                myImagesDao.deleteImage(id);
            }
        });
    }

    public LiveData<List<MyImages>> getAllImages(){
        return myImagesDao.getAllImages();
    }

    public LiveData<MyImages> getImageById(int id){
        return myImagesDao.getImageById(id);
    }































}


