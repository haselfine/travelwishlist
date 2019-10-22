package com.example.travelwishlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.travelwishlist.db.Place;
import com.example.travelwishlist.db.PlaceRepository;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {

    private PlaceRepository mPlaceRepository;
    private LiveData<List<Place>> mAllPlaces;

    public PlaceViewModel(@NonNull Application application){
        super(application);
        mPlaceRepository = new PlaceRepository(application);
        mAllPlaces = mPlaceRepository.getAllPlaces();
    }

    public LiveData<List<Place>> getAllPlaces(){
        return mAllPlaces;
    }

    public void insert(Place place){
        mPlaceRepository.insert(place);
    }

    public void update(Place place){mPlaceRepository.update(place);}

    public void delete(Place place){mPlaceRepository.delete(place);}

    public void insert(Place... places){mPlaceRepository.insert(places);}
}
