package com.bitsanddroids.myplants.plants;
import java.io.Serializable;

import lombok.Data;

@Data
public class Plant implements Serializable {

    private String name;
    private String ImageUrl;
    private int sun;
    private int placement;
    private int toxic;
    private int water;
    private int edible;
    private String plantID;
    private String plantingTime;

    public Plant(){

    }

    public Plant(String name, String imageUrl, int sun, int placement, int toxic, int water, int edible, String plantingTime) {
        this.name = name;
        this.ImageUrl = imageUrl;
        this.sun = sun;
        this.placement = placement;
        this.toxic = toxic;
        this.water = water;
        this.edible = edible;
        this.plantingTime = plantingTime;
    }

}
