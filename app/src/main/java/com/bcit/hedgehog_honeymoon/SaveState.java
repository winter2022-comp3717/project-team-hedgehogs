package com.bcit.hedgehog_honeymoon;
import android.content.Context;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.io.File;
import java.util.Scanner;

public class SaveState extends JSONObject{
    public SaveState(String jsonString) throws JSONException {
        super(jsonString);
    }
    public SaveState(){

    }
}

class SaveManager{
    SaveState currentState;
    Context context;
    public SaveManager(Context context){
        //TODO find a way to store save data on device. Check if it exitst here, else create new
        //? Why is is a JSON object? A little more trouble to deal with in Java,
        // but easier and more universal to save than a Java class, obviously Kat.
        this.context = context.getApplicationContext();
        if(doesASaveStateExist()){
            loadSaveGameFromDevice();
        } else {
            createNewSaveState();
        }

    }

    public SaveState getCurrentSaveState(){
        return currentState;
    }

    //Save the currently loaded saveState onto the disk
    public void saveSaveStateToDevice(){
        File saveFile;
        if(doesASaveStateExist()){
            saveFile = getSaveStateFromDisk();
        } else {
            saveFile = new File(context.getFilesDir(), "hedgeHogSave.json");
        }

        try {
            FileWriter writer = new FileWriter(saveFile);
            writer.write(currentState.toString());
            writer.flush();
            writer.close();
            System.out.println(currentState.toString());
            System.out.println("A string");
        } catch (IOException e) {
            System.out.println("Error saving file");
            e.printStackTrace();
        }

    }

    //Overloaded function that takes the new save state.
    public void saveSaveStateToDevice(SaveState stateToSave){
        File saveFile;
        if(doesASaveStateExist()){
            saveFile = getSaveStateFromDisk();
        } else {
            saveFile = new File(context.getFilesDir(), "hedgeHogSave.json");
        }

        try {
            FileWriter writer = new FileWriter(saveFile);
            writer.write(currentState.toString());
            writer.flush();
            writer.close();
            System.out.println(currentState.toString());
        } catch (IOException e) {
            System.out.println("Error saving file");
            e.printStackTrace();
        }

    }

    //Get the File objet from Disk
    public File getSaveStateFromDisk(){
        File file = new File(context.getFilesDir(), "hedgeHogSave.json");

            if(doesASaveStateExist()){
                System.out.println("File already exists");
            } else {
                createNewSaveState();
                saveSaveStateToDevice();
            }

        return file;
    }

    //Once we have saving figured out, this will be how we load
    public void loadSaveGameFromDevice(){
        File file = getSaveStateFromDisk();
        try {
            Scanner scan = new Scanner(file);
            String JSONString = scan.next();
            currentState = (SaveState) new SaveState(JSONString);;
        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
        }

    }

    //TODO Add a way for this to actually check a thing
    public boolean doesASaveStateExist(){
        File file = new File(context.getFilesDir(), "hedgeHogSave.json");

            if(file.exists() && !file.isDirectory()){
                return true;
            } else {
                return false;
            }

    }

    //Delete the save state from the game and from the device. Careful with this boy.
    public void __deleteAllSaveData(){

    }

    //From internal storage, getFilesDir() or getCacheDir()
    //
    //From external storage, getExternalFilesDir() or getExternalCacheDir()

    public void createNewSaveState(){
        currentState = new SaveState();
        try {
            currentState.put("currentHedgehogs", 0);
            currentState.put("totalHedgehogs", 0);

            currentState.put("mealworms", 0);
            currentState.put("safaris", 0);
            currentState.put("ladyhogs", 0);

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
        saveSaveStateToDevice();
    }
}
