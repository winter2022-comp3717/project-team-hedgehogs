package com.bcit.hedgehog_honeymoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_game);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUpRecyclerView(arr);

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
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Menu_Home)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.Menu_settings) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("TRUE", true);
            startActivity(intent);
        } else {
            System.out.println(item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }


    //Add a hedgehog manually by clicking
    public void incrementHedgehog(){
        totalHedgehogs++;
        currentHedgehogs++;
        updateUI();
        System.out.println("Clicked the hed hog!");
        System.out.println("Number of hedgehogs : " + totalHedgehogs);
        //Save at each manual click
        updateSaveState();
        saveManager.saveSaveStateToDevice(currentSaveState);
    }

    public void updateSaveState(){
        try {
            currentSaveState.put("totalHedgehogs", totalHedgehogs);
            currentSaveState.put("currentHedgehogs", currentHedgehogs);

            currentSaveState.put("SFX", false);
            currentSaveState.put("Music", false);
            currentSaveState.put("Volume", 50);

            currentSaveState.put("numberOfUpgrade1", 0);
            currentSaveState.put("numberOfUpgrade2", 0);
            currentSaveState.put("numberOfUpgrade3", 0);
            currentSaveState.put("numberOfUpgrade4", 0);
            currentSaveState.put("numberOfUpgrade5", 0);
            currentSaveState.put("numberOfUpgrade6", 0);
            currentSaveState.put("numberOfUpgrade7", 0);
            currentSaveState.put("numberOfUpgrade8", 0);
            currentSaveState.put("numberOfUpgrade9", 0);

            currentSaveState.put("Event1", false);
            currentSaveState.put("Event2", false);
            currentSaveState.put("Event3", false);
            currentSaveState.put("Event4", false);
            currentSaveState.put("Event5", false);
            currentSaveState.put("Event6", false);
            currentSaveState.put("Event7", false);
            currentSaveState.put("Event8", false);
            currentSaveState.put("Event9", false);
            currentSaveState.put("Event10", false);
            currentSaveState.put("Event11", false);
            currentSaveState.put("Event12", false);
            currentSaveState.put("Event13", false);
            currentSaveState.put("Event14", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    private void setUpRecyclerView(PowerUps[] data) {
        RecyclerView rv = findViewById(R.id.recycler_game);
        PowerUpRecycler adapter = new PowerUpRecycler(data);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    PowerUps [] arr = new PowerUps[]{
            new PowerUps ("Mealworm", "Does some stuff to make u get more hedgies", "Cost: 100", R.drawable.mealworm),
            new PowerUps ("Hedgehog Safari", "Does some stuff to make u get more hedgies", "Cost: 100", R.drawable.safari),
            new PowerUps ("Lady Hog", "Does some stuff to make u get more hedgies", "Cost: 100", R.drawable.ladyhog)
    };

}