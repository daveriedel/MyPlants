package com.bitsanddroids.myplants.plants;
import lombok.Data;

@Data
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

}
