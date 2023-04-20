package com.example.rickandmorty.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rickandmorty.R;
import com.example.rickandmorty.model.LocationModel;
import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.response.LocationResponse;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.MyViewHolder> {

    int row_index = 0;
    private Context context;
    private LocationResponse locationList;
    private ItemClickListener clickListener;

    private boolean isLoading;

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
    public LocationListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_location,parent,false);
        return new LocationListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtLocationName.setText(this.locationList.getLocations().get(position).getName().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < locationList.getLocations().size(); i++) {
                    row_index=position;
                    notifyDataSetChanged();
                }
                clickListener.onLocationClick(locationList.getLocations().get(position));
            }
        });
        if(row_index==position){
            holder.cardLocation.setCardBackgroundColor(Color.rgb(65, 180, 201));
        }
        else
        {
            holder.cardLocation.setCardBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        if (this.locationList != null){
            return this.locationList.getLocations().size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtLocationName;
        CardView cardLocation;
        public MyViewHolder(View itemView){
            super(itemView);
            txtLocationName = itemView.findViewById(R.id.txtLocationName);
            cardLocation = itemView.findViewById(R.id.cardLocation);
        }
    }

    public interface ItemClickListener{
        public void onLocationClick(LocationModel location);
    }
}

