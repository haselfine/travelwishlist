package com.example.travelwishlist.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlaceDAO {

    @Query("SELECT * FROM place ORDER BY UPPER(name) DESC")
    LiveData<List<Place>> getAllPlaces();

    @Insert
    void insert(Place place);

    @Update
    void update(Place place);

    @Delete
    void delete(Place place);

    @Insert
    void insert(Place... places);

    @Delete
    void delete(Place... places);

    @Update
    void update(Place... places);

    @Query("DELETE FROM place WHERE id = :id")
    void delete(int id);
}
