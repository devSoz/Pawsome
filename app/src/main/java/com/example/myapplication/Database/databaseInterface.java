package com.example.myapplication.Database;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Model.favDoge;
import java.util.List;

@androidx.room.Dao
public interface databaseInterface {

    @Insert
    void insert(favDoge model);

    @Update
    void update(favDoge model);

    @Delete
    void delete(favDoge model);

    @Query("DELETE FROM FaveDoge WHERE imageId = :imageId")
    void deleteByImageId(String imageId);

    @Query("select * from FaveDoge ORDER BY id ASC")
    List<favDoge>getFavDoge();

    //@Query("SELECT * FROM FaveDoge ORDER BY id ASC")
    //LiveData<List<favDoge>> getFavDoge();
}

