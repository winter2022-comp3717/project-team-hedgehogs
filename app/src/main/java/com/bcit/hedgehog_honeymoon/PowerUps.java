package com.bcit.hedgehog_honeymoon;

public class PowerUps {
    String name;
    String description;
    int cost;
    int imageId;

    public PowerUps(String name, String description, int cost, int imageId) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
