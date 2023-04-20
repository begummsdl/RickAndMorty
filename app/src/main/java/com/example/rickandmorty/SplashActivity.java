package com.example.rickandmorty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity  extends AppCompatActivity {

    private TextView welcomeText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        welcomeText = findViewById(R.id.welcome_text);
        sharedPreferences = getPreferences(MODE_PRIVATE);

        boolean firstTime = sharedPreferences.getBoolean("firstTime", true);
        if (firstTime) {
            welcomeText.setText("Welcome!");
            sharedPreferences.edit().putBoolean("firstTime", false).apply();
        } else {
            welcomeText.setText("Hello!");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000); // 3 saniye bekleyin


    }
}
