package com.bitsanddroids.myplants.mainview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bitsanddroids.myplants.R
import com.bitsanddroids.myplants.plants.PersonalPlant
import com.bitsanddroids.myplants.userauthentication.User
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

import java.util.ArrayList
import java.util.Objects

class PersonalPlantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()
        initRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        adapter!!.notifyDataSetChanged()
    }

    fun initPlants() {

        val docRef = db!!.collection("users").document(Objects.requireNonNull<String>(auth!!.uid))
        docRef.get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<User>(User::class.java!!)
            personalPlants.clear()
            personalPlants.addAll(user!!.personalPlants!!)
            adapter!!.notifyDataSetChanged()
        }


    }

    fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = PersonalCustomRecycleViewAdapter(personalPlants, this@PersonalPlantActivity)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        initPlants()
    }

    companion object {
        private val personalPlants = ArrayList<PersonalPlant>()
        private var auth: FirebaseAuth? = null
        private var db: FirebaseFirestore? = null
        private var user: User? = null

        private var adapter: PersonalCustomRecycleViewAdapter? = null

        fun deletePlant(position: Int) {

            val ref = db!!.collection("users").document(Objects.requireNonNull<FirebaseUser>(auth!!.currentUser).getUid())

            if (user != null) {

                personalPlants.removeAt(position)
                Log.d("INDEX", Integer.toString(position))
                user!!.personalPlants = personalPlants

                ref.set(user!!)
                adapter!!.notifyItemRemoved(position)
                adapter!!.notifyItemRangeChanged(position, adapter!!.itemCount)


            }


        }
    }
}