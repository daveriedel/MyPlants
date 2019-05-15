package com.bitsanddroids.myplants.userauthentication


import android.util.Log
import com.bitsanddroids.myplants.mainview.PersonalPlantActivity
import com.bitsanddroids.myplants.plants.PersonalPlant
import com.bitsanddroids.myplants.plants.Plant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.io.Serializable


class User : Serializable {

    var userId: String = ""
    var username: String = ""
    var personalPlants: ArrayList<PersonalPlant>? = null
    var language: Int = 0


    constructor() {

    }

    constructor(userId: String, username: String, personalPlants: ArrayList<PersonalPlant>?, language: Int) {
        this.userId = userId
        this.username = username
        this.personalPlants = personalPlants
        this.language = language
    }


    fun addPlant(plant: Plant) {

        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser!!
        var user: User = this
        this.personalPlants = user.personalPlants
        if (firebaseUser != null) {
            val personalPlant = PersonalPlant(plant.name, plant.imageUrl, plant.sun, plant.placement, plant.toxic,
                    plant.water, plant.edible, null, plant.plantingTime, null, plant.plantingTime, userId)
            this.personalPlants!!.add(personalPlant)
            val db = FirebaseFirestore.getInstance()
            val personalPlantsRef = db.collection("users").document(user.userId)
            personalPlantsRef.set(user)

        }

    }

    fun deletePlant(position: Int) {
        val user: User = this
        val db = FirebaseFirestore.getInstance()

        personalPlants!!.removeAt(position)

        PersonalPlantActivity.personalPlants.clear()
        PersonalPlantActivity.personalPlants.addAll(personalPlants!!)

        Log.d("DEBUG", this.userId)

        db.collection("users").document(this.userId).set(user)


        PersonalPlantActivity.adapter!!.notifyItemRemoved(position)
        PersonalPlantActivity.adapter!!.notifyItemRangeChanged(position, PersonalPlantActivity.adapter!!.itemCount)


    }

}
