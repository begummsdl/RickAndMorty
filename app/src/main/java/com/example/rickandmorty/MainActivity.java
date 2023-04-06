package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.rickandmorty.adapter.CharacterListAdapter;
import com.example.rickandmorty.model.CharacterModel;
import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.viewmodel.CharacterListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CharacterResponse characterModelList;
    private CharacterListAdapter characterListAdapter;
    private CharacterListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView characterRecyclerView = findViewById(R.id.characterRecyclerView);
        final TextView noResult = findViewById(R.id.noResultCharacter);
        LinearLayoutManager layoutManager = new GridLayoutManager(this,1);
        characterRecyclerView.setLayoutManager(layoutManager);
        //32.dkyı eklemedik
        characterListAdapter = new CharacterListAdapter(this,characterModelList);
        characterRecyclerView.setAdapter(characterListAdapter);

        viewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);
        viewModel.getCharactersListObserver().observe(this, new Observer<CharacterResponse>() {
            @Override
            public void onChanged(CharacterResponse characterModels) {
                if (characterModels != null){
                    characterModelList = characterModels;
                    characterListAdapter.setCharacterList(characterModels);
                    noResult.setVisibility(View.GONE);
                }else{
                    noResult.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.makeApiCall();
    }

}