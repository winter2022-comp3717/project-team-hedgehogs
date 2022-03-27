package com.bcit.hedgehog_honeymoon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

//    BackgroundSound mBackgroundSound = new BackgroundSound();
    private Intent musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView_main, new ImageHedgehogFragment());
        fragmentTransaction.commit();
        View dummyView = findViewById(R.id.fragmentContainerView_main);
        PlayBackgroundSound(dummyView);

    }

    public void PlayBackgroundSound(View view) {
        musicService = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(musicService);
    }

    public void goToGameActivity(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void openSettings(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView_main, new SettingsFragment());
        fragmentTransaction.commit();
    }

    public void openSettings() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView_main, new SettingsFragment());
        fragmentTransaction.commit();
    }

}