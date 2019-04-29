package com.bitsanddroids.myplants.userauthentication;

import android.util.Log;

import com.bitsanddroids.myplants.plants.PersonalPlant;
import com.bitsanddroids.myplants.plants.Plant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class User {
    private String userId;
    private String username;
    private ArrayList<PersonalPlant> personalPlants = new ArrayList<>();

    public User(){

    }

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
    public User(String userId, String username, ArrayList<PersonalPlant> personalPlants) {
        this.userId = userId;
        this.username = username;
        this.personalPlants = personalPlants;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<PersonalPlant> getPersonalPlants() {
        return personalPlants;
    }

    public void addPlant(PersonalPlant personalPlant){
        personalPlants.add(personalPlant);

    }

    public void deletePlant(int position){
        personalPlants.remove(position);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPersonalPlants(ArrayList<PersonalPlant> personalPlants) {
        this.personalPlants = personalPlants;
    }
}
