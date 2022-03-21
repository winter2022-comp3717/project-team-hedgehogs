package com.bcit.hedgehog_honeymoon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Choreographer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Choreographer.FrameCallback;

public class GameActivity extends AppCompatActivity implements Choreographer.FrameCallback{

    public Event events = new Event();

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

        gameIsRunning = true;
        hedgehogPicture = (ImageView) findViewById(R.id.imageView_game);

        hedgehogPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementHedgehog();
            }
        });


    }

    //Add a hedgehog manually by clicking
    public void incrementHedgehog(){
        totalHedgehogs++;
        currentHedgehogs++;
        //updateUI();
        System.out.println("Clicked the hed hog!");
        System.out.println("Number of hedgehogs : " + totalHedgehogs);
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
        totalHedgeHogTextView.setText(totalHedgehogs);
        currentHedgeHogTextView.setText(currentHedgehogs);
    }

    //This will execute every frame for hedgehog automation
    public void doFrame(long time){
        checkForAutomation();
        updateUI();
        checkForEvents();
    }

    public void checkForAutomation(){
        //check for new hedgehogs from the shop items, update hedgehog counter
    }

    public void checkForEvents(){
        //if some milestone, run an event with the even data from the event object
    }
}