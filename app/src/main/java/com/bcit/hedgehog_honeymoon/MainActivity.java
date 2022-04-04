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

public class MainActivity extends AppCompatActivity implements MainInterface{

//    BackgroundSound mBackgroundSound = new BackgroundSound();
    private Intent musicService;

    private SoundPlayer soundPlayer;

    private boolean isSettingsOpen = false;

    private boolean isMusicOn = true;
    private boolean isSfxOn = true;

    private SettingsClass settingsObject;

    public SaveState currentSaveState;
    public SaveManager saveManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Access old save state to check if music and sfx should be on or off
        saveManager = new SaveManager(getApplicationContext());
        currentSaveState = saveManager.getCurrentSaveState();
        try {
            isMusicOn = currentSaveState.getBoolean("Music");
            isSfxOn = currentSaveState.getBoolean("SFX");
        } catch(Exception e) {
            System.out.println("oops. could not access either 'Music' or 'SFX key-value" +
                    " pairs from 'saveState'");
        }

        // Setup default hedgehog fragment for main menu (hedgehog fragment has no features apart
        // from having a cute hedgehog in the main menu, and to be a placeholder for when fragment
        // is replaced by 'settingsFragment' when settings button is clicked)
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView_main, new ImageHedgehogFragment());
        fragmentTransaction.commit();


        // Open settings when hitting 'settings' button from navbar
        View dummyView = findViewById(R.id.fragmentContainerView_main);

        Intent gameIntent = getIntent();
        if(gameIntent.hasExtra("OPENSETTINGS")){
                System.out.println("CONTAINS KEY for opening settings");
                boolean[] settingsArray = gameIntent.getBooleanArrayExtra("OPENSETTINGS");
                isMusicOn = settingsArray[0];
                isSfxOn = settingsArray[1];
                openSettings(dummyView);
            } else if(getIntent().hasExtra("OPENMENU")){
                System.out.println("CONTAINS KEY for opening menu");
                boolean[] settingsArray = gameIntent.getBooleanArrayExtra("OPENMENU");
                isMusicOn = settingsArray[0];
                isSfxOn = settingsArray[1];
            } else {
            System.out.println("NO KEY HERE");
        }

        // Start background music
        if (isMusicOn) {
            PlayBackgroundSound(dummyView);
        }
    }



    public void PlayBackgroundSound(View view) {
        musicService = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(musicService);
    }

    public void goToGameActivity(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        boolean[] settingsArray = new boolean[2];
        settingsArray[0] = isMusicOn;
        settingsArray[1] = isSfxOn;
        intent.putExtra("OPENGAME", settingsArray);
        startActivity(intent);
    }

    public void openSettings(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //SettingsClass settingsObject = new SettingsClass(true, false);
        if(!isSettingsOpen) {
            fragmentTransaction.replace(R.id.fragmentContainerView_main,
                    SettingsFragment.newInstance(new SettingsClass(isMusicOn, isSfxOn)));
            fragmentTransaction.commit();

            isSettingsOpen = true;
        } else {
            fragmentTransaction.replace(R.id.fragmentContainerView_main, new ImageHedgehogFragment());
            fragmentTransaction.commit();

            isSettingsOpen = false;
        }
    }

    public void clearData(View view){
        SaveManager saveManager = new SaveManager(getApplicationContext());
        saveManager.clearAllData();
    }

    @Override
    public boolean[] getSettingsContext() {
        boolean settingsContext[] = new boolean[2];
        settingsContext[0] = isMusicOn;
        settingsContext[1] = isSfxOn;
        return settingsContext;
    }

    public void setSettingsContextMusic(boolean newIsMusicOnBool) {
        isMusicOn = newIsMusicOnBool;
    }

    public void setSettingsContextSfx(boolean newIsSfxOnBool) {
        isSfxOn = newIsSfxOnBool;
    }
}

