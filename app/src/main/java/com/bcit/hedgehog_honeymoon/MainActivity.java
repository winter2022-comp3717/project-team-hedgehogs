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

    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlayer = new SoundPlayer(this);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView_main, new ImageHedgehogFragment());
        fragmentTransaction.commit();
        View dummyView = findViewById(R.id.fragmentContainerView_main);
        PlayBackgroundSound(dummyView);
        System.out.println(getIntent());

        if(getIntent().hasExtra("OPENSETTINGS")){
                System.out.println("CONTAINS KEY");
                openSettings();
            } else {
                System.out.println("NO KEY HERE");
            }
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

    public void hitButton() {
        soundPlayer.playHitSound();
    }

    public void clearData(View view){
        SaveManager saveManager = new SaveManager(getApplicationContext());
        saveManager.clearAllData();
    }

}