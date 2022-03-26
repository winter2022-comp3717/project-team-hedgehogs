package com.bcit.hedgehog_honeymoon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Choreographer;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

public class GameActivity extends AppCompatActivity implements Choreographer.FrameCallback{

    public Event events = new Event();
    public SaveState currentSaveState;
    public SaveManager saveManager;

    public TextView totalHedgeHogTextView;
    public TextView currentHedgeHogTextView;
    public int totalHedgehogs;
    public int currentHedgehogs;
    public ImageView hedgehogPicture;
    public boolean gameIsRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        totalHedgeHogTextView = findViewById(R.id.textView_game_lifetime);
        currentHedgeHogTextView = findViewById(R.id.textView_game_current);
        gameIsRunning = true;
        hedgehogPicture = (ImageView) findViewById(R.id.imageView_game);

        hedgehogPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementHedgehog();
            }
        });

        saveManager = new SaveManager(getApplicationContext());
        currentSaveState = saveManager.getCurrentSaveState();
        assignHedgehogsFromSave();
        updateUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_game, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //Add a hedgehog manually by clicking
    public void incrementHedgehog(){
        totalHedgehogs++;
        currentHedgehogs++;
        updateUI();
        System.out.println("Clicked the hed hog!");
        System.out.println("Number of hedgehogs : " + totalHedgehogs);
        saveManager.saveSaveStateToDevice();
    }

    //Increment by a larger amount, for automation
    public void incrementHedgehog(int newHedgehogs){
        totalHedgehogs+= newHedgehogs;
        currentHedgehogs += newHedgehogs;
    }

    public void removeHedgehogs(int numberToRemove){
        currentHedgehogs -= numberToRemove;
    }

    //Update the UI on screen
    public void updateUI(){
        totalHedgeHogTextView.setText(Integer.toString(totalHedgehogs));
        currentHedgeHogTextView.setText(Integer.toString(currentHedgehogs));
    }

    public void assignHedgehogsFromSave(){
        try {
            int newCurrentHedgehogs = currentSaveState.getInt("currentHedgehogs");
            int newTotalHedgehogs = currentSaveState.getInt("totalHedgehogs");
            currentHedgehogs = newCurrentHedgehogs;
            totalHedgehogs = newTotalHedgehogs;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //This will execute every frame for hedgehog automation
    public void doFrame(long time){
        checkForAutomation();
        updateUI();
        checkForEvents();
        System.out.println("This is happening a lot!");
    }

    

    public void checkForAutomation(){
        //check for new hedgehogs from the shop items, update hedgehog counter
    }

    public void checkForEvents(){

    }
}