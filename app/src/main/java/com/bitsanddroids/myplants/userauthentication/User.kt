package com.bitsanddroids.myplants.userauthentication


import lombok.Data

import com.bitsanddroids.myplants.plants.PersonalPlant
import com.bitsanddroids.myplants.plants.Plant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

import java.io.Serializable
import java.util.ArrayList

@Data
class User(var userId: String = "", var username: String = "", var personalPlants: ArrayList<PersonalPlant>? = null, var language: Int = 0) : Serializable {



    fun addPlant(plant: Plant) {

        val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val user:User = this
        if (firebaseUser != null) {
            val personalPlant = PersonalPlant(plant.name, plant.imageUrl, plant.sun, plant.placement, plant.toxic,
                    plant.water, plant.edible, null, plant.plantingTime, null, plant.plantingTime, userId)
            personalPlants!!.add(personalPlant)
            val db = FirebaseFirestore.getInstance()
            val personalPlantsRef = db.collection("users").document(user.userId)
            personalPlantsRef.set(user)

        }

    }


}
