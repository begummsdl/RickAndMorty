package com.example.rickandmorty.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.R;
import com.example.rickandmorty.model.LocationModel;
import com.example.rickandmorty.response.LocationResponse;

public class LocationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int rowIndex = 0;
    private Context context;
    private LocationResponse locationList;
    private ItemClickListener clickListener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public LocationListAdapter(Context context, LocationResponse locationList, ItemClickListener clickListener) {
        this.context = context;
        this.locationList = locationList;
        this.clickListener = clickListener;
    }

    public void setCharacterList(LocationResponse locationList){
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_location, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return this.locationList != null ? this.locationList.getLocations().size() : 0;
    }

    public int getItemViewType(int position) {
        return this.locationList.getLocations().get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setRowIndex(int index){
        this.rowIndex = index;
    }

    public class ItemViewHolder  extends RecyclerView.ViewHolder{
        TextView txtLocationName;
        CardView cardLocation;
        public ItemViewHolder (View itemView){
            super(itemView);
            txtLocationName = itemView.findViewById(R.id.txtLocationName);
            cardLocation = itemView.findViewById(R.id.cardLocation);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        viewHolder.txtLocationName.setText(this.locationList.getLocations().get(position).getName().toString());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < locationList.getLocations().size(); i++) {
                    rowIndex=position;
                    notifyDataSetChanged();
                }
                clickListener.onLocationClick(locationList.getLocations().get(position));
            }
        });
        if(rowIndex==position){
            viewHolder.cardLocation.setCardBackgroundColor(Color.rgb(65, 180, 201));
        }
        else
        {
            viewHolder.cardLocation.setCardBackgroundColor(Color.WHITE);
        }

    }

    public interface ItemClickListener{
        public void onLocationClick(LocationModel location);
    }
}

