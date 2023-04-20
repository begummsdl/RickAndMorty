package com.example.rickandmorty;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.rickandmorty.adapter.CharacterListAdapter;
import com.example.rickandmorty.adapter.LocationListAdapter;
import com.example.rickandmorty.model.CharacterModel;
import com.example.rickandmorty.model.LocationModel;
import com.example.rickandmorty.response.LocationResponse;
import com.example.rickandmorty.viewmodel.CharacterListViewModel;
import com.example.rickandmorty.viewmodel.LocationListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListAdapter.ItemClickListener,CharacterListAdapter.ItemClickListener {

    RecyclerView characterRecyclerView, locationRecyclerView;

    TextView noResult;

    // for character api
    private List<CharacterModel> characterModelList;
    private CharacterListAdapter characterListAdapter;
    private CharacterListViewModel characterViewModel;

    // for location api
    private LocationResponse locationModelList;
    private LocationListAdapter locationListAdapter;
    private LocationListViewModel locationViewModel;
    int currentPage = 1;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        registerEventHandlers();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

    }

    private void initComponents() {
        characterRecyclerView = findViewById(R.id.characterRecyclerView);
        locationRecyclerView = findViewById(R.id.locationRecyclerView);
        noResult = findViewById(R.id.characterNotFound);
    }

    private void registerEventHandlers() {
        getLocation();
    }

    private void getLocation(){
        locationRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false);
        locationRecyclerView.setLayoutManager(layoutManager);
        locationListAdapter = new LocationListAdapter(this,locationModelList,this);
        locationRecyclerView.setAdapter(locationListAdapter);
        locationViewModel = ViewModelProviders.of(this).get(LocationListViewModel.class);

        locationViewModel.getLocationsListObserver().observe(this, new Observer<LocationResponse>() {
            @Override
            public void onChanged(LocationResponse locationResponse) {

                List<String> charactersUrl = locationResponse.getLocations().get(0).getResidents();
                String ids = "";
                for (String characterUrl:charactersUrl){
                    ids += characterUrl.substring(characterUrl.lastIndexOf("/") + 1);
                    ids += ",";
                }
                if (ids != "") {
                    ids= ids.substring(0,ids.length()- 1);
                    getCharacter(ids);
                    noResult.setVisibility(View.GONE);
                } else {
                    characterListAdapter.clear();
                    characterRecyclerView.setAdapter(characterListAdapter);
                    noResult.setVisibility(View.VISIBLE);
                }

                if (locationResponse != null){
                    locationModelList = locationResponse;
                    locationListAdapter.setCharacterList(locationResponse);
                }else{
                }
            }
        });

        locationViewModel.makeApiCall(currentPage);
        locationRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading){
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == locationModelList.getLocations().size() - 1) {
                        currentPage += 1;
                        if (currentPage<8){
                            loadMore(currentPage);
                        }
                        else{
                            isLoading=true;
                        }
                    }
                }
            }
        });

    }

    private void loadMore(int page) {

        locationModelList.getLocations().add(null);
        locationListAdapter.notifyItemInserted(locationModelList.getLocations().size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                locationModelList.getLocations().remove(locationModelList.getLocations().size() - 1);
                int scrollPosition = locationModelList.getLocations().size();
                locationListAdapter.notifyItemRemoved(scrollPosition);

                locationViewModel.makeApiCall(page);
                locationListAdapter.setRowIndex(0);
                locationRecyclerView.scrollToPosition(0);

                locationListAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);

    }

    private void getCharacter(String ids){
        characterRecyclerView.setHasFixedSize(true);
        characterRecyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        characterListAdapter = new CharacterListAdapter(this,characterModelList,this);
        characterRecyclerView.setAdapter(characterListAdapter);
        characterViewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);
        characterViewModel.getCharactersListObserver().observe(this, new Observer<List<CharacterModel>>() {
            @Override
            public void onChanged(List<CharacterModel> characterResponse) {
                if (characterResponse != null){
                    characterModelList = characterResponse;
                    characterListAdapter.setCharacterList(characterResponse);
                    noResult.setVisibility(View.GONE);
                } else {
                    characterListAdapter.clear();
                    characterRecyclerView.setAdapter(characterListAdapter);
                    noResult.setVisibility(View.VISIBLE);
                }
            }
        });
        characterViewModel.makeApiCall(ids);
    }

    @Override
    public void onLocationClick(LocationModel location) {
        List<String> charactersUrl = location.getResidents();
        String ids = "";
        for (String characterUrl:charactersUrl){
            ids += characterUrl.substring(characterUrl.lastIndexOf("/") + 1);
            ids += ",";
        }
        if (ids != "") {
            ids= ids.substring(0,ids.length()- 1);
            getCharacter(ids);
            noResult.setVisibility(View.GONE);
        } else {
            characterListAdapter.clear();
            characterRecyclerView.setAdapter(characterListAdapter);
            noResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCharacterClick(CharacterModel character) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("character", character);
        intent.putExtra("originName", character.getOrigin().getName());
        intent.putExtra("locationName", character.getLocation().getName());
        startActivity(intent);
    }
}