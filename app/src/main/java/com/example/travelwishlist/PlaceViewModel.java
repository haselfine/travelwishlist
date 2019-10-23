package com.example.travelwishlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.travelwishlist.db.Place;
import com.example.travelwishlist.db.PlaceRepository;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel { //this is the communication point for the rest of the app

    private PlaceRepository mPlaceRepository;
    private LiveData<List<Place>> mAllPlaces;

    public PlaceViewModel(@NonNull Application application){
        super(application);
        mPlaceRepository = new PlaceRepository(application);
        mAllPlaces = mPlaceRepository.getAllPlaces(); //retrieve all places in the database
    }

    public LiveData<List<Place>> getAllPlaces(){
        return mAllPlaces;
    }

    public void insert(Place place){ //add location to database
        mPlaceRepository.insert(place);
    }

    public void update(Place place){mPlaceRepository.update(place);} //if I were to add an "edit" functionality, this would be useful

    public void delete(Place place){mPlaceRepository.delete(place);} //deletes place from database

    public void insert(Place... places){mPlaceRepository.insert(places);} //adds multiple places to database (this is unnecessary at the moment)
}
