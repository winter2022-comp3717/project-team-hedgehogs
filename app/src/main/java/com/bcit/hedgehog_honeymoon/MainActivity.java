package com.bcit.hedgehog_honeymoon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView_main, new ImageHedgehogFragment());
        fragmentTransaction.commit();

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

}