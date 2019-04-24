package com.bitsanddroids.myplants.plants;

import java.util.Date;

public class PersonalPlant {

    private String name;
    private String ImageUrl;
    private int sun;
    private int placement;
    private int toxic;
    private int water;
    private boolean edible;
    private Date watered;
    private String plantID;
    private String placementNote;

    private String userID;

    public PersonalPlant(){

    }

    public PersonalPlant(String name, String imageUrl, int sun, int placement, int toxic, int water, boolean edible, String userID) {
        this.name = name;
        this.ImageUrl = imageUrl;
        this.sun = sun;
        this.placement = placement;
        this.toxic = toxic;
        this.water = water;
        this.edible = edible;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public int getSun() {
        return sun;
    }

    public int getPlacement() {
        return placement;
    }

    public int getToxic() {
        return toxic;
    }

    public int getWater() {
        return water;
    }

    public boolean isEdible() {
        return edible;
    }

    public Date getWatered() {
        return watered;
    }

    public String getPlacementNote() {
        return placementNote;
    }

    public String getUserID() {
        return userID;
    }
    public String getPlantID(){
        return plantID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setSun(int sun) {
        this.sun = sun;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public void setToxic(int toxic) {
        this.toxic = toxic;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    public void setWatered(Date watered) {
        this.watered = watered;
    }

    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }

    public void setPlacementNote(String placementNote) {
        this.placementNote = placementNote;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
