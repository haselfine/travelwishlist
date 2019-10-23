package com.example.travelwishlist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelwishlist.db.Place;

import java.util.Collections;
import java.util.List;

public class WishlistFragment extends Fragment implements WishListListener {

    private PlaceViewModel mPlaceViewModel;
    private List<Place> mPlaces;

    private static final String TAG = "WISH_LIST_FRAGMENT";

    //private WishlistFragmentListener mListener;

    private WishListAdapter wishListAdapter;


    private Button mAddButton;
    private EditText mNewPlaceNameEditText;
    private EditText mReasonEditText;

    public WishlistFragment(){

    }

    public static WishlistFragment newInstance(){ //instantiate fragment
        WishlistFragment fragment = new WishlistFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        final PlaceViewModel placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);

        final Observer<List<Place>> placeListObserver = new Observer<List<Place>>() { //updates view when data has changed
            @Override
            public void onChanged(List<Place> places) {
                Log.d(TAG, "Places changed: " + places);
                Collections.sort(places); //sorts by name
                WishlistFragment.this.mPlaces = places;
                WishlistFragment.this.wishListAdapter.setPlaces(places);
                WishlistFragment.this.wishListAdapter.notifyDataSetChanged();
            }
        };

        placeViewModel.getAllPlaces().observe(this, placeListObserver); //retrieve data set of places
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.wish_list); //connect recycler view to view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager); //set layout manager
        wishListAdapter = new WishListAdapter(this.getContext(), this); //connect adapter
        wishListAdapter.setPlaces(mPlaces); //connect places to adapter
        recyclerView.setAdapter(wishListAdapter); //connect adapter to recycler view

        mAddButton = view.findViewById(R.id.add_place_button);
        mNewPlaceNameEditText = view.findViewById(R.id.new_place_name);
        mReasonEditText = view.findViewById(R.id.reason);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPlace = mNewPlaceNameEditText.getText().toString();
                String reason = mReasonEditText.getText().toString();
                if (newPlace.isEmpty()||reason.isEmpty()){
                    Toast.makeText(WishlistFragment.this.getContext(), "Must add place name and reason", Toast.LENGTH_LONG).show(); //must add place name and reason
                    return;
                }

                Place place = new Place(newPlace,reason);
                mPlaces.add(place); //I don't think I need this part anymore? Leaving it just in case
                mPlaceViewModel.insert(place); //insert into database
                wishListAdapter.notifyItemInserted(mPlaces.size() - 1);
                mNewPlaceNameEditText.getText().clear(); //clear edittexts on entry
                mReasonEditText.getText().clear();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context){ //nothing in here yet
        super.onAttach(context);
    }

    @Override
    public void onStart(){
        super.onStart();
        mPlaceViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class); //connect view model to fragment
    }

    @Override
    public void onListClick(int position) { //search for location in google maps
        Place place = mPlaces.get(position);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(place.getName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
        startActivity(mapIntent);

    }

    @Override
    public void onListLongClick(final int position) {
        final Place place = mPlaces.get(position);

        if(getActivity() == null){ //removes possibility of nullpointerexception
            return;
        }
        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(getActivity()) //pops up a dialog box
                .setMessage(getString(R.string.delete_place_message, mPlaces.get(position).getName()))//asks if they want to delete the location
                .setTitle(getString(R.string.delete_dialog_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPlaceViewModel.delete(place);
                        wishListAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        confirmDeleteDialog.show();
    }
}
