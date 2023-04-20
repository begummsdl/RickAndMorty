package com.example.rickandmorty;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rickandmorty.model.CharacterModel;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private CharacterModel character;

    String origin,location;

    private TextView txtName,txtStatus,txtSpecy,txtGender,txtOrigin,txtLocation,txtEpisodes,txtCreated;
    private ImageView characterImage,turnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initComponents();
        registerEventHandlers();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

    }

    private void initComponents(){
        turnBack = findViewById(R.id.btnTurnBack);
        txtName = findViewById(R.id.txtName);
        txtStatus = findViewById(R.id.txtStatus);
        txtSpecy = findViewById(R.id.txtSpecy);
        txtGender = findViewById(R.id.txtGender);
        txtOrigin = findViewById(R.id.txtOrigin);
        txtLocation = findViewById(R.id.txtLocation);
        txtEpisodes = findViewById(R.id.txtEpisodes);
        txtCreated = findViewById(R.id.txtCreated);
        characterImage = findViewById(R.id.characterImage);
        character = getIntent().getParcelableExtra("character");
        origin = getIntent().getStringExtra("originName");
        location = getIntent().getStringExtra("locationName");
    }

    private void registerEventHandlers() {
       setDetails();
       turnBackIntent();
    }

    private void setDetails(){
        txtName.setText(character.getName());
        txtStatus.setText(character.getStatus());
        txtSpecy.setText(character.getSpecies());
        txtGender.setText(character.getGender());
        txtOrigin.setText(origin);
        txtLocation.setText(location);
        List<String> characterEpisodes = character.getEpisode();
        String ids = "";
        for (String characterEpisode:characterEpisodes){
            ids += characterEpisode.substring(characterEpisode.lastIndexOf("/") + 1);
            ids += ",";
        }

        if (ids != "") {
            ids= ids.substring(0,ids.length()- 1);
            //Toast.makeText(this,ids,Toast.LENGTH_SHORT).show();
            txtEpisodes.setText(ids);
        } else {
            txtEpisodes.setText("not found episodes.");
        }

        txtCreated.setText(character.getCreated());
        Glide.with(this)
                .load(character.getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(characterImage);
    }

    private void turnBackIntent(){
        turnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
