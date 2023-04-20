package com.example.rickandmorty.response;

import com.example.rickandmorty.model.Info;
import com.example.rickandmorty.model.LocationModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationResponse {

    @SerializedName("info")
    @Expose
    private Info info;

    @SerializedName("results")
    @Expose
    private List<LocationModel> locations;

    public Info getInfo() {
        return info;
    }

    public List<LocationModel> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return "LocationResponse{" +
                "info=" + info +
                ", locations=" + locations +
                '}';
    }
}
