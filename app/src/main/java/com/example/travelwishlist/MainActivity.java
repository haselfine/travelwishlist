package com.example.travelwishlist;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.travelwishlist.db.Place;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MAIN_ACTIVITY";

    private static final String TAG_WISHLIST = "WishlistFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WishlistFragment wishlistFragment = WishlistFragment.newInstance(); //There's really no reason for it to be a fragment at this point.
                                                                            //But just in case I need to add fragments later, I'm ready

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(android.R.id.content, wishlistFragment, TAG_WISHLIST);
        ft.commit();

        PlaceViewModel placeViewModel = new PlaceViewModel(getApplication());

        LiveData<List<Place>> wishList = placeViewModel.getAllPlaces();

        wishList.observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                Log.d(TAG, "Places: " + places);
            }
        });
    }
}
