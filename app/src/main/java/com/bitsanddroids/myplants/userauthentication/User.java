package com.bitsanddroids.myplants.userauthentication;


import lombok.Data;

import com.bitsanddroids.myplants.plants.PersonalPlant;

import java.io.Serializable;
import java.util.ArrayList;

@Data

public class User implements Serializable {
    private String userId;
    private String username;
    private int language;
    private ArrayList<PersonalPlant> personalPlants = new ArrayList<>();

    public User(){

    }

    User(String userId, String username, int language) {
        this.userId = userId;
        this.username = username;
        this.language = language;
    }
    public User(String userId, String username, ArrayList<PersonalPlant> personalPlants) {
        this.userId = userId;
        this.username = username;
        this.personalPlants = personalPlants;
    }



    public void addPlant(PersonalPlant personalPlant){
        personalPlants.add(personalPlant);

    }


}
