package com.example.rickandmorty.response;

import com.example.rickandmorty.model.CharacterModel;
import com.example.rickandmorty.model.Info;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterResponse {
    @SerializedName("info")
    @Expose
    private Info info;

    @SerializedName("results")
    @Expose
    private List<CharacterModel> characters;

    public Info getInfo() {
        return info;
    }

    public List<CharacterModel> getCharacters() {
        return characters;
    }

    @Override
    public String toString() {
        return "CharacterSearchResponse{" +
                "info=" + info +
                ", characters=" + characters +
                '}';
    }
}
