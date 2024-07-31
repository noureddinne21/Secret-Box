package com.nouroeddinne.secretbox;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface MyImagesDao {

    @Insert
    void insert(MyImages myImages);

    @Delete
    void delete(MyImages myImages);

    @Update
    void update(MyImages myImages);

    @Query("SELECT * FROM myImages ORDER BY image_id ASC")
    LiveData<List<MyImages>>getAllImages();

    @Query("SELECT * FROM myImages WHERE image_id = :id LIMIT 1")
    LiveData<MyImages> getImageById(int id);

    @Query("DELETE FROM myImages WHERE image_id = :id")
    void deleteImage (int id);

}
