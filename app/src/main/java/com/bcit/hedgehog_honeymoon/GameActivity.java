package com.bcit.hedgehog_honeymoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

public class GameActivity extends AppCompatActivity{
    public SaveState currentSaveState;
    public SaveManager saveManager;

    public TextView totalHedgeHogTextView;
    public TextView currentHedgeHogTextView;
    public float totalHedgehogs;
    public float currentHedgehogs;
    public ImageView hedgehogPicture;
    public boolean gameIsRunning;

    public static int numberOfMealWorms;
    int mealWormPrice;

    public static int numberOfSafaris;
    int safariPrice;

    public static int numberOfLadyHogs;
    int ladyHogPrice;


    public static boolean event1FLag = false;
    public static boolean event2FLag = false;
    public static boolean event3FLag = false;
    public static boolean event4FLag = false;
    public static boolean event5FLag = false;
    public static boolean event6FLag = false;
    public static boolean event7FLag = false;
    public static boolean event8FLag = false;
    public static boolean event9FLag = false;
    public static boolean event10FLag = false;

    private SoundPlayer soundPlayer;

    Handler handler = new Handler();
    int delay = 100;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        totalHedgeHogTextView = findViewById(R.id.textView_game_lifetime);
        currentHedgeHogTextView = findViewById(R.id.textView_game_current);
        gameIsRunning = true;
        hedgehogPicture = (ImageView) findViewById(R.id.imageView_game);
        soundPlayer = new SoundPlayer(this);

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
                hitButton();
            }
        });

        saveManager = new SaveManager(getApplicationContext());
        currentSaveState = saveManager.getCurrentSaveState();
        assignHedgehogsFromSave();
        setEventFlagsFromSaveState();
        setUpgradesFromSaveState();
        updateUI();
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable, delay);
                checkForAutomation();
            }
        }, delay);
        super.onResume();
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
            intent.putExtra("OPENSETTINGS", true);
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
        checkForEvents();
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

            currentSaveState.put("mealworms", numberOfMealWorms);
            currentSaveState.put("safaris", numberOfSafaris);
            currentSaveState.put("ladyhogs", numberOfLadyHogs);

            currentSaveState.put("numberOfUpgrade1", 0);
            currentSaveState.put("numberOfUpgrade2", 0);
            currentSaveState.put("numberOfUpgrade3", 0);
            currentSaveState.put("numberOfUpgrade4", 0);
            currentSaveState.put("numberOfUpgrade5", 0);
            currentSaveState.put("numberOfUpgrade6", 0);
            currentSaveState.put("numberOfUpgrade7", 0);
            currentSaveState.put("numberOfUpgrade8", 0);
            currentSaveState.put("numberOfUpgrade9", 0);

            currentSaveState.put("Event1", event1FLag);
            currentSaveState.put("Event2", event2FLag);
            currentSaveState.put("Event3", event3FLag);
            currentSaveState.put("Event4", event4FLag);
            currentSaveState.put("Event5", event5FLag);
            currentSaveState.put("Event6", event6FLag);
            currentSaveState.put("Event7", event7FLag);
            currentSaveState.put("Event8", event8FLag);
            currentSaveState.put("Event9", event9FLag);
            currentSaveState.put("Event10", event10FLag);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Increment by a larger amount, for automation
    public void incrementHedgehog(float newHedgehogs){
        totalHedgehogs+= newHedgehogs;
        currentHedgehogs += newHedgehogs;
        updateUI();
    }

    public void removeHedgehogs(int numberToRemove){
        currentHedgehogs -= numberToRemove;
    }

    //Update the UI on screen
    public void updateUI(){
        totalHedgeHogTextView.setText(formatHedgehogs(totalHedgehogs));
        currentHedgeHogTextView.setText(formatHedgehogs(currentHedgehogs));
        setUpRecyclerView(arr);
    }

    public String formatHedgehogs(float hedHogFloat){
        if(hedHogFloat == (long) hedHogFloat){
            return String.format("%.2f", (float) hedHogFloat);
        } else {
            return String.format("%.2f", hedHogFloat);
        }
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

    public void checkForAutomation(){
        float totalHedgeHogsToAdd = 0;
        totalHedgeHogsToAdd += (numberOfMealWorms * 0.25) / 10f;
        totalHedgeHogsToAdd += numberOfSafaris / 10f;
        totalHedgeHogsToAdd += (numberOfLadyHogs * 5) / 10f;
        incrementHedgehog((float) totalHedgeHogsToAdd);
    }

    public void checkForEvents(){
        if(totalHedgehogs > 10 && !event1FLag) {
            event1FLag = true;
            playEvent(1);
            return;
        }
        if(totalHedgehogs > 100 && !event2FLag){
            event2FLag = true;
            playEvent(2);
            return;
        }
        if(totalHedgehogs > 500 && !event3FLag){
            event3FLag = true;
            playEvent(3);
            return;
        }
        if(totalHedgehogs > 1250 && !event4FLag){
            event4FLag = true;
            playEvent(4);
            return;
        }
        if(totalHedgehogs > 3000 && !event5FLag){
            event5FLag = true;
            playEvent(5);
            return;
        }
        if(totalHedgehogs > 10000 && !event6FLag){
            event6FLag = true;
            playEvent(6);
            return;
        }
    }

    public void playEvent(int eventNumber){
        Intent intent = new Intent(this, StoryActivity.class);

        if(eventNumber == 1){
            intent.putExtra("TEXT", R.string.event_1);
            intent.putExtra("IMG", R.drawable.tapp);
        } else if (eventNumber == 2){
            intent.putExtra("TEXT", R.string.event_2);
            intent.putExtra("IMG", R.drawable.tapp);
        } else if (eventNumber == 3){
            intent.putExtra("TEXT", R.string.event_3);
            intent.putExtra("IMG", R.drawable.kitkat);
        } else if (eventNumber == 4){
            intent.putExtra("TEXT", R.string.event_4);
            intent.putExtra("IMG", R.drawable.tapp);
        } else if (eventNumber == 5){
            intent.putExtra("TEXT", R.string.event_5);
            intent.putExtra("IMG", R.drawable.safari);
        } else if (eventNumber == 6){
            intent.putExtra("TEXT", R.string.event_6);
            intent.putExtra("IMG", R.drawable.kitkat);
        }

        startActivity(intent);
    }

    private void setUpRecyclerView(PowerUps[] data) {
        RecyclerView rv = findViewById(R.id.recycler_game);
        PowerUpRecycler adapter = new PowerUpRecycler(data);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(position == 0){
                            incrementMealWorms();
                        }
                        if(position == 1){
                            incrementSafari();
                        }
                        if(position == 2){
                            incrementLadies();
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    public void incrementMealWorms(){
        if(currentHedgehogs > mealWormPrice){
            currentHedgehogs -= mealWormPrice;
            numberOfMealWorms++;
        }
        updateUI();
    }

    public void incrementSafari(){
        if(currentHedgehogs > safariPrice){
            currentHedgehogs -= safariPrice;
            numberOfSafaris++;
        }
        updateUI();
    }

    public void incrementLadies(){
        if(currentHedgehogs > ladyHogPrice){
            currentHedgehogs -= ladyHogPrice;
            numberOfLadyHogs++;
        }
        updateUI();
    }

    PowerUps [] arr = new PowerUps[]{
            new PowerUps ("Mealworm", "Hedgie-boys love these little snacks! Lay'em out around the ranch to attract spikers!", 50, R.drawable.mealworm),
            new PowerUps ("Hedgehog Safari", "Hire an employee to help you gather up those snorf-hogs!", 350, R.drawable.safari),
            new PowerUps ("Lady Hog", "Strait from happy scritches HQ, a lady-hog is sure to attract lots of hedgers to the ranch!", 100, R.drawable.ladyhog)
    };

    public void setEventFlagsFromSaveState(){
        try {
            event1FLag = currentSaveState.getBoolean("Event1");
            event2FLag = currentSaveState.getBoolean("Event2");
            event3FLag = currentSaveState.getBoolean("Event3");
            event4FLag = currentSaveState.getBoolean("Event4");
            event5FLag = currentSaveState.getBoolean("Event5");
            event6FLag = currentSaveState.getBoolean("Event6");
            event7FLag = currentSaveState.getBoolean("Event7");
            event8FLag = currentSaveState.getBoolean("Event8");
            event9FLag = currentSaveState.getBoolean("Event9");
            event10FLag = currentSaveState.getBoolean("Even10");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setUpgradesFromSaveState(){
        try {
            numberOfMealWorms =  currentSaveState.getInt("mealworms");
            numberOfSafaris =  currentSaveState.getInt("safaris");
            numberOfLadyHogs =  currentSaveState.getInt("ladyhogs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void hitButton() {
        soundPlayer.playHitSound();
    }

}