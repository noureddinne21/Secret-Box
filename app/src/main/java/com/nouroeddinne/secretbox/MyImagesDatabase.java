package com.nouroeddinne.secretbox;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = MyImages.class,version = 1)
public abstract class MyImagesDatabase extends RoomDatabase {

    private static MyImagesDatabase inestens;

    public abstract MyImagesDao myImagesDao();
    static ExecutorService executorService = Executors.newFixedThreadPool(1);


    public static synchronized MyImagesDatabase getInestens(Context context){

        if(inestens == null){
            inestens= Room.databaseBuilder(context.getApplicationContext(),MyImagesDatabase.class,"myImageDatabase").fallbackToDestructiveMigration().build();
        }
        return inestens;

    }


    
}
