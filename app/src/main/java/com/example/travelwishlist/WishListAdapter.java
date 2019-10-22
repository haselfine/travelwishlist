package com.example.travelwishlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelwishlist.db.Place;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private final static String TAG = "WISHLIST_ADAPTER";

    interface ListEventListener{
        void onDeletePlace(int position);
    }

    private ListEventListener mEventListener;

    private List<Place> mPlaces;

    private WishListClickListener listener;

    public WishListAdapter(List<Place> data, WishListClickListener listener, ListEventListener eventListener){
        this.listener = listener;
        this.mPlaces = data;
        this.mEventListener = eventListener;

    }

    void setPlaces(List<Place> places){
        this.mPlaces = places;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list_element, parent, false);
        WishListViewHolder viewHolder = new WishListViewHolder(layout, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) {
        if(mPlaces != null){
            Place place = mPlaces.get(position);
            holder.bind(place);
        } else {
            holder.bind(null);
        }
    }

    @Override
    public int getItemCount() {
        if(mPlaces == null){
            return 0;
        }
        return mPlaces.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        LinearLayout layout;
        TextView nameTextView;
        TextView reasonTextView;
        TextView dateCreatedTextView;

        WishListClickListener listener;


        public WishListViewHolder(@NonNull LinearLayout layout, WishListClickListener listener){
            super(layout);
            this.listener = listener;
            this.layout = layout;
            nameTextView = layout.findViewById(R.id.placeNameTextView);
            reasonTextView = layout.findViewById(R.id.reasonTextView);
            dateCreatedTextView = layout.findViewById(R.id.dateCreatedTextView);

            layout.setOnClickListener(this);
            layout.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view){
            listener.onListClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onListLongClick(getAdapterPosition());
            return true;
        }

        void bind(Place place){
            Log.d(TAG, "binding place " + place);
            if(place == null){
                nameTextView.setText("");
                reasonTextView.setText("");
                dateCreatedTextView.setText("");
            } else {
                nameTextView.setText(place.getName());
                reasonTextView.setText(place.getReason());
                dateCreatedTextView.setText(place.getDate());
            }
        }
    }
}
