package com.bitsanddroids.myplants.plants;

public class Plant {

    private String name;
    private String ImageUrl;
    private int sun;
    private int placement;
    private int toxic;
    private int water;
    private boolean edible;
    private String plantID;

    public Plant(){

    }

    public Plant(String name, String imageUrl, int sun, int placement, int toxic, int water, boolean edible) {
        this.name = name;
        this.ImageUrl = imageUrl;
        this.sun = sun;
        this.placement = placement;
        this.toxic = toxic;
        this.water = water;
        this.edible = edible;
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

    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }
}
