package com.bitsanddroids.myplants.plants;

public class Plant {

    private String name;
    private String ImageUrl;
    private int sun;
    private int placement;
    private int toxic;
    private int water;
    private boolean edible;

    public Plant(){

    }

    public Plant(String name, String imageUrl, int sun, int placement, int toxic, int water, boolean edible) {
        this.name = name;
        ImageUrl = imageUrl;
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
}
