package com.bitsanddroids.myplants.userauthentication;

import com.bitsanddroids.myplants.plants.PersonalPlant;
import com.bitsanddroids.myplants.plants.Plant;

import java.util.ArrayList;

public class User {
    private String userId;
    private String username;
    private ArrayList<PersonalPlant> personalPlants;

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.personalPlants = new ArrayList<>();
    }
}
