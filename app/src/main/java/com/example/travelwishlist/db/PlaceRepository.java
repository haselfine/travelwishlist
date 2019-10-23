package com.example.travelwishlist.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlaceRepository {

    private PlaceDAO placeDAO;

    public PlaceRepository(Application application){ //this is the communication point for the view model
        PlaceDatabase db = PlaceDatabase.getDatabase(application);
        placeDAO = db.placeDAO();
    }

    public LiveData<List<Place>> getAllPlaces(){
        return placeDAO.getAllPlaces();
    }

    public void insert(Place place){
        new InsertPlaceAsyncTask(placeDAO).execute(place);
    }

    public void delete(Place place){
        new DeletePlaceAsyncTask(placeDAO).execute(place);
    }

    public void update(Place... places){
        new UpdatePlaceAsync(placeDAO).execute(places);
    }

    public void insert(Place... places){
        new InsertPlaceAsyncTask(placeDAO).execute(places);
    }

    public void delete(Place... places){
        new DeletePlaceAsyncTask(placeDAO).execute(places);
    }

    public void delete(int id){new DeletePlaceIDAsyncTask(placeDAO).execute(id);}

    private static class UpdatePlaceAsync extends AsyncTask<Place, Void, Void> { //queries are performed in the background

        private PlaceDAO placeDAO;

        UpdatePlaceAsync(PlaceDAO placeDAO) {
            this.placeDAO = placeDAO;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDAO.update(places);
            return null;
        }
    }

    private static class InsertPlaceAsyncTask extends AsyncTask<Place, Void, Void>{ //queries in background
        private PlaceDAO dao;

        public InsertPlaceAsyncTask(PlaceDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Place... places){
            dao.insert(places);
            return null;
        }
    }

    private static class DeletePlaceAsyncTask extends AsyncTask<Place, Void, Void>{ //in background
        PlaceDAO dao;

        public DeletePlaceAsyncTask(PlaceDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Place... places){
            dao.delete(places[0]);
            return null;
        }
    }

    private static class DeletePlaceIDAsyncTask extends AsyncTask<Integer, Void, Void>{ //in background

        PlaceDAO dao;

        public DeletePlaceIDAsyncTask(PlaceDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Integer... id){
            dao.delete(id[0]);
            return null;
        }
    }
}
