package com.bitsanddroids.myplants.plants;

import lombok.Data;
import java.util.Date;

@Data
public class PersonalPlant {

    private String name;
    private String ImageUrl;
    private int sun;
    private int placement;
    private int toxic;
    private int water;
    private int edible;
    private Date watered;
    private String plantID;
    private String placementNote;

    private String userID;

    public PersonalPlant(){

    }

    public PersonalPlant(String name, String imageUrl, int sun, int placement, int toxic, int water, int edible, String userID) {
        this.name = name;
        this.ImageUrl = imageUrl;
        this.sun = sun;
        this.placement = placement;
        this.toxic = toxic;
        this.water = water;
        this.edible = edible;
        this.userID = userID;
    }
}
