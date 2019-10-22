package com.example.travelwishlist.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlaceRepository {

    private PlaceDAO mPlaceDAO;

    public PlaceRepository(Application application){
        PlaceDatabase db = PlaceDatabase.getDatabase(application);
        mPlaceDAO = db.placeDAO();
    }

    public LiveData<List<Place>> getAllPlaces(){
        return mPlaceDAO.getAllPlaces();
    }

    public void insert(Place place){
        new InsertPlaceAsyncTask(mPlaceDAO).execute(place);
    }

    public void delete(Place place){
        new DeletePlaceAsyncTask(mPlaceDAO).execute(place);
    }

    public void update(Place... places){
        new UpdateAsyncTask(mPlaceDAO).execute(places);
    }

    public void insert(Place... places){
        new InsertPlaceAsyncTask(mPlaceDAO).execute(places);
    }

    public void delete(Place... places){
        new DeletePlaceAsyncTask(mPlaceDAO).execute(places);
    }

    public void delete(int id){new DeletePlaceIDAsyncTask(mPlaceDAO).execute(id);}

    private static class UpdateAsyncTask extends AsyncTask<Place, Void, Void> {

        private PlaceDAO asyncTaskDAO;

        UpdateAsyncTask(PlaceDAO placeDAO) {
            this.asyncTaskDAO = placeDAO;
        }

        @Override
        protected Void doInBackground(Place... places) {
            asyncTaskDAO.update(places);
            return null;
        }
    }

    private static class InsertPlaceAsyncTask extends AsyncTask<Place, Void, Void>{
        PlaceDAO dao;

        public InsertPlaceAsyncTask(PlaceDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Place... places){
            dao.insert(places[0]);
            return null;
        }
    }

    private static class DeletePlaceAsyncTask extends AsyncTask<Place, Void, Void>{
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

    private static class DeletePlaceIDAsyncTask extends AsyncTask<Integer, Void, Void>{

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
