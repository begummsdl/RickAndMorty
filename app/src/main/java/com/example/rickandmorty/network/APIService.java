package com.example.rickandmorty.network;

import com.example.rickandmorty.model.CharacterModel;
import com.example.rickandmorty.model.LocationModel;
import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.response.LocationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("character/")
    Call<CharacterResponse> getCharacter(
            @Query("page") int page
    );

    @GET("character/{ids}")
    Call<List<CharacterModel>> getCharacterListByIds(@Path("ids") String ids);

    @GET("character/{id}")
    Call<CharacterModel> getCharacterById(@Path("id") String id);


    @GET("location/")
    Call<LocationResponse> getLocation(
            @Query("page") int page
    );


}
