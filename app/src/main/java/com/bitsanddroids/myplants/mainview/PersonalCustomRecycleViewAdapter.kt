package com.bitsanddroids.myplants.mainview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bitsanddroids.myplants.R
import com.bitsanddroids.myplants.plants.PersonalPlant
import com.bitsanddroids.myplants.plants.Plant
import com.bitsanddroids.myplants.userauthentication.User
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

import java.util.ArrayList
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class PersonalCustomRecycleViewAdapter(private val personalPlants: ArrayList<PersonalPlant>, private val mContext: Context) : RecyclerView.Adapter<PersonalCustomRecycleViewAdapter.ViewHolder>() {

    //when the viewholder is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Loading the child layout to populate the recyclerview
        val view = LayoutInflater.from(parent.context).inflate(R.layout.personal_plant_recyclerview_item, parent, false)

        return ViewHolder(view)

    }

    //setup each individual recyclerview item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Set plantName Textview to plants name
        holder.plantName.text = personalPlants[position].name

        //Load image as bitmap from image URL to imageView
        Glide.with(mContext)
                .asBitmap()
                .load(personalPlants[position].imageUrl)
                .into(holder.plantImage)
        Log.d("DEBUG", personalPlants[position].imageUrl)
        //check if user is signed in if not hide add to account button
        if (FirebaseAuth.getInstance().currentUser == null) {
            holder.deleteButton.visibility = View.INVISIBLE
        }

        holder.deleteButton.setOnClickListener { PersonalPlantActivity.deletePlant(position) }

        holder.parentLayout.setOnClickListener { Toast.makeText(mContext, personalPlants[position].name, Toast.LENGTH_SHORT).show() }


    }

    override fun getItemCount(): Int {
        return personalPlants.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var plantImage: CircleImageView
        var placementIcon: ImageView
        var sunIcon: ImageView
        var plantName: TextView
        var deleteButton: Button
        var parentLayout: ConstraintLayout


        init {
            plantImage = itemView.findViewById(R.id.personalPlantImageView)
            placementIcon = itemView.findViewById(R.id.placementImageView)
            sunIcon = itemView.findViewById(R.id.sunImageView)
            parentLayout = itemView.findViewById(R.id.personalPlantConstraintLayout)
            plantName = itemView.findViewById(R.id.personalPlantNameTextView)
            deleteButton = itemView.findViewById(R.id.deleteButton)
        }
    }
}




