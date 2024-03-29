package com.bcit.hedgehog_honeymoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    public static int mealWormPrice;

    public static int numberOfSafaris;
    public static int safariPrice;

    public static int numberOfLadyHogs;
    public static int ladyHogPrice;


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
    public static boolean event11FLag = false;
    public static boolean event12FLag = false;
    public static boolean event13FLag = false;
    public static boolean event14FLag = false;
    public static boolean event15FLag = false;

    private SoundPlayer soundPlayer;

    private boolean isMusicOn = true;
    private boolean isSfxOn = false;

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

        Intent mainActivityIntent = getIntent();
        if(mainActivityIntent.hasExtra("OPENGAME")){
            System.out.println("CONTAINS KEY for opening game");
            boolean[] settingsArray = mainActivityIntent.getBooleanArrayExtra("OPENGAME");
            isMusicOn = settingsArray[0];
            isSfxOn = settingsArray[1];
        } else {
            System.out.println("NO KEY HERE");
        }

        if(isSfxOn) {
            hedgehogPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    incrementHedgehog();
                    hitButton();
                }
            });
        } else {
            hedgehogPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    incrementHedgehog();
                }
            });
        }


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
            boolean[] settingsArray = new boolean[4];
            settingsArray[0] = isMusicOn;
            settingsArray[1] = isSfxOn;
            intent.putExtra("OPENMENU", settingsArray);
            startActivity(intent);
        } else if(item.getItemId() == R.id.Menu_settings) {
            Intent intent = new Intent(this, MainActivity.class);
            boolean[] settingsArray = new boolean[4];
            settingsArray[0] = isMusicOn;
            settingsArray[1] = isSfxOn;
            intent.putExtra("OPENSETTINGS", settingsArray);
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

            currentSaveState.put("SFX", isSfxOn);
            currentSaveState.put("Music", isMusicOn);
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
            currentSaveState.put("Event11", event10FLag);
            currentSaveState.put("Event12", event10FLag);
            currentSaveState.put("Event13", event10FLag);
            currentSaveState.put("Event14", event10FLag);
            currentSaveState.put("Event15", event10FLag);
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
        updatePrices();
        setUpRecyclerView(arr);
    }

    @SuppressLint("DefaultLocale")
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
        totalHedgeHogsToAdd += (numberOfMealWorms * 0.5) / 10f;
        totalHedgeHogsToAdd += numberOfSafaris * 2 / 10f;
        totalHedgeHogsToAdd += (numberOfLadyHogs * 8) / 10f;
        incrementHedgehog((float) totalHedgeHogsToAdd);
    }

    public void checkForEvents(){
        if(totalHedgehogs > 10 && !event1FLag) {
            event1FLag = true;
            playEvent(1);
            return;
        }
        else if(totalHedgehogs > 100 && !event2FLag){
            event2FLag = true;
            playEvent(2);
            return;
        }
        else if(totalHedgehogs > 500 && !event3FLag){
            event3FLag = true;
            playEvent(3);
            return;
        }
        else if(totalHedgehogs > 1250 && !event4FLag){
            event4FLag = true;
            playEvent(4);
            return;
        }
        else if(totalHedgehogs > 3000 && !event5FLag){
            event5FLag = true;
            playEvent(5);
            return;
        }
        else if(totalHedgehogs > 10000 && !event6FLag){
            event6FLag = true;
            playEvent(6);
            return;
        }
        else if(totalHedgehogs > 20000 && !event7FLag){
            event7FLag = true;
            playEvent(7);
            return;
        }
        else if(totalHedgehogs > 40000 && !event8FLag){
            event8FLag = true;
            playEvent(8);
            return;
        }
        else if(totalHedgehogs > 80000 && !event9FLag){
            event9FLag = true;
            playEvent(9);
            return;
        }
        else if(totalHedgehogs > 100000 && !event10FLag){
            event10FLag = true;
            playEvent(10);
            return;
        }
        else if(totalHedgehogs > 120000 && !event11FLag){
            event11FLag = true;
            playEvent(11);
            return;
        }
        else if(totalHedgehogs > 150000 && !event12FLag){
            event12FLag = true;
            playEvent(12);
            return;
        }
        else if(totalHedgehogs > 200000 && !event13FLag){
            event13FLag = true;
            playEvent(13);
            return;
        }
        else if(totalHedgehogs > 750000 && !event14FLag){
            event14FLag = true;
            playEvent(14);
            return;
        }
        else if(currentHedgehogs > 1000000 && !event15FLag){
            event15FLag = true;
            playEvent(15);
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
        } else if (eventNumber == 7){
            intent.putExtra("TEXT", R.string.event_7);
            intent.putExtra("IMG", R.drawable.tapp);
        } else if (eventNumber == 8){
            intent.putExtra("TEXT", R.string.event_8);
            intent.putExtra("IMG", R.drawable.kitkat);
        } else if (eventNumber == 9){
            intent.putExtra("TEXT", R.string.event_9);
            intent.putExtra("IMG", R.drawable.tapp);
        } else if (eventNumber == 10){
            intent.putExtra("TEXT", R.string.event_10);
            intent.putExtra("IMG", R.drawable.kitkat);
        } else if (eventNumber == 11){
            intent.putExtra("TEXT", R.string.event_11);
            intent.putExtra("IMG", R.drawable.agent);
        } else if (eventNumber == 12){
            intent.putExtra("TEXT", R.string.event_12);
            intent.putExtra("IMG", R.drawable.kitkat);
        } else if (eventNumber == 13){
            intent.putExtra("TEXT", R.string.event_13);
            intent.putExtra("IMG", R.drawable.agent);
        } else if (eventNumber == 14){
            intent.putExtra("TEXT", R.string.event_14);
            intent.putExtra("IMG", R.drawable.kitkat);
        } else if (eventNumber == 15){
            intent.putExtra("TEXT", R.string.event_15);
            intent.putExtra("IMG", R.drawable.radio);
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

    public void updatePrices(){
        mealWormPrice = 50 + (numberOfMealWorms * 25);
        safariPrice = 250 + (numberOfSafaris * 75);
        ladyHogPrice = 500 + (numberOfLadyHogs * 150);
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
            new PowerUps ("Lady Hog", "Straight from Happy Scritches HQ, a lady-hog is sure to attract lots of hedgers to the ranch!", 100, R.drawable.ladyhog)
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
            event11FLag = currentSaveState.getBoolean("Even11");
            event12FLag = currentSaveState.getBoolean("Even12");
            event13FLag = currentSaveState.getBoolean("Even13");
            event14FLag = currentSaveState.getBoolean("Even14");
            event15FLag = currentSaveState.getBoolean("Even15");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setUpgradesFromSaveState(){
        try {
            numberOfMealWorms =  currentSaveState.getInt("mealworms");
            numberOfSafaris =  currentSaveState.getInt("safaris");
            numberOfLadyHogs =  currentSaveState.getInt("ladyhogs");

            mealWormPrice = 50 + (numberOfMealWorms * 25);
            safariPrice = 250 + (numberOfSafaris * 75);
            ladyHogPrice = 500 + (numberOfLadyHogs * 150);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void hitButton() {
        soundPlayer.playHitSound();
    }

}