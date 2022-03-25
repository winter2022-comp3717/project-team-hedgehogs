package com.bcit.hedgehog_honeymoon;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

public class SaveState extends JSONObject{ }

class SaveManager{
    SaveState currentState;
    public SaveManager(){
        //TODO find a way to store save data on device. Check if it exitst here, else create new
        //? Why is is a JSON object? A little more trouble to deal with in Java,
        // but easier and more universal to save than a Java class, obviously Kat.
        if(doesASaveStateExist()){
            loadSaveGameFromDevice();
        } else {
            createNewSaveState();
        }
    }

    public SaveState getCurrentSaveState(){
        return currentState;
    }

    //Once we have saving figured out, this will be the method to save
    public void saveSaveStateToDevice(){

    }

    //Once we have saving figured out, this will be how we load
    public void loadSaveGameFromDevice(){

    }

    //TODO Add a way for this to actually check a thing
    public boolean doesASaveStateExist(){
        return false;
    }

    //Delete the save state from the game and from the device. Careful with this boy.
    public void __deleteAllSaveData(){

    }

    public void createNewSaveState(){
        currentState = new SaveState();
        try {
            currentState.put("currentHedgehogs", 69);
            currentState.put("totalHedgehogs", 40);

            currentState.put("numberOfUpgrade1", 0);
            currentState.put("numberOfUpgrade2", 0);
            currentState.put("numberOfUpgrade3", 0);
            currentState.put("numberOfUpgrade4", 0);
            currentState.put("numberOfUpgrade5", 0);
            currentState.put("numberOfUpgrade6", 0);
            currentState.put("numberOfUpgrade7", 0);
            currentState.put("numberOfUpgrade8", 0);
            currentState.put("numberOfUpgrade9", 0);

            currentState.put("Event1", false);
            currentState.put("Event2", false);
            currentState.put("Event3", false);
            currentState.put("Event4", false);
            currentState.put("Event5", false);
            currentState.put("Event6", false);
            currentState.put("Event7", false);
            currentState.put("Event8", false);
            currentState.put("Event9", false);
            currentState.put("Event10", false);
            currentState.put("Event11", false);
            currentState.put("Event12", false);
            currentState.put("Event13", false);
            currentState.put("Event14", false);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
