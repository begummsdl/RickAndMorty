package com.example.rickandmorty.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.rickandmorty.model.LocationModel;
import com.example.rickandmorty.network.APIService;
import com.example.rickandmorty.network.RetroInstance;
import com.example.rickandmorty.response.LocationResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListViewModel extends ViewModel {

    private MutableLiveData<LocationResponse> locationsList;

    public LocationListViewModel() {
        locationsList = new MutableLiveData<>();
    }

    public MutableLiveData<LocationResponse> getLocationsListObserver(){
        return locationsList;
    }

    public void makeApiCall(int page){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<LocationResponse> call = apiService.getLocation(page);
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.code() == 200){
                    Log.v("Tag","the response"+response.body().toString());
                    List<LocationModel> locations = new ArrayList<>(response.body().getLocations());
                    locationsList.postValue(response.body());
                    for (LocationModel location:locations){
                        Log.v("Tag","character name: "+location.getName());
                    }

                }
                else {
                    try {
                        Log.v("Tag","Error"+response.errorBody().string());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });
    }
}