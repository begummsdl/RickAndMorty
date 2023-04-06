package com.example.rickandmorty.network;

import com.example.rickandmorty.model.CharacterModel;
import com.example.rickandmorty.model.LocationModel;
import com.example.rickandmorty.response.CharacterResponse;

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

    @GET("character/{id}")
    Call<CharacterModel> getCharacterListById(@Path("id") int id);


}
