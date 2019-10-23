package com.example.travelwishlist;

import android.content.Context;
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


    private List<Place> mPlaces;

    private WishListListener listener;

    public WishListAdapter(Context context, WishListListener listener){
        this.listener = listener;

    }

    void setPlaces(List<Place> places){ //tells app that data has changed
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
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) { //binds the recycler items to the view, I believe?
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

        TextView nameTextView;
        TextView reasonTextView;
        TextView dateCreatedTextView;

        WishListListener listener;


        public WishListViewHolder(@NonNull View itemView, WishListListener listener){
            super(itemView);
            this.listener = listener;
            nameTextView = itemView.findViewById(R.id.placeNameTextView);
            reasonTextView = itemView.findViewById(R.id.reasonTextView);
            dateCreatedTextView = itemView.findViewById(R.id.dateCreatedTextView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view){ //tells app that short click has been performed
            listener.onListClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) { //tells app that long click has been performed
            listener.onListLongClick(getAdapterPosition());
            return true;
        }

        void bind(Place place){
            Log.d(TAG, "binding place " + place);
            if(place == null){
                nameTextView.setText("");
                reasonTextView.setText("");
                dateCreatedTextView.setText("");
            } else { //sets text for each recycler item
                nameTextView.setText(place.getName());
                reasonTextView.setText(place.getReason());
                dateCreatedTextView.setText(place.getDate().toString());
            }
        }
    }
}
