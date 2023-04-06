package com.example.rickandmorty.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rickandmorty.model.CharacterModel;
import com.example.rickandmorty.network.APIService;
import com.example.rickandmorty.network.RetroInstance;
import com.example.rickandmorty.response.CharacterResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterListViewModel extends ViewModel {

    private MutableLiveData<CharacterResponse> charactersList;

    public CharacterListViewModel() {
        charactersList = new MutableLiveData<>();
    }

    public MutableLiveData<CharacterResponse> getCharactersListObserver(){
        return charactersList;
    }

    public void makeApiCall(){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<CharacterResponse> call = apiService.getCharacter(2);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.code() == 200){
                    Log.v("Tag","the response"+response.body().toString());
                    List<CharacterModel> characters = new ArrayList<>(response.body().getCharacters());
                    charactersList.postValue(response.body());
                    for (CharacterModel character:characters){
                        Log.v("Tag","character name: "+character.getName());
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
            public void onFailure(Call<CharacterResponse> call, Throwable t) {

            }
        });
                /*.enqueue(new Callback<List<CharacterModel>>() {
            @Override
            public void onResponse(Call<List<CharacterModel>> call, Response<List<CharacterModel>> response) {
                charactersList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CharacterModel>> call, Throwable t) {
                charactersList.postValue(null);
            }
        });*/
    }
}
