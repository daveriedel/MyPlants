package com.bitsanddroids.myplants.mainview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bitsanddroids.myplants.R
import com.bitsanddroids.myplants.infoPages.PlantInfoActivity
import com.bitsanddroids.myplants.plants.PersonalPlant
import com.bitsanddroids.myplants.plants.Plant
import com.bitsanddroids.myplants.userauthentication.User
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class CustomRecycleViewAdapter(private val plants: ArrayList<Plant>, private val mContext: Context) : RecyclerView.Adapter<CustomRecycleViewAdapter.ViewHolder>() {
    private val personalPlants: ArrayList<PersonalPlant>? = null
    lateinit var user: User
    lateinit var db: FirebaseFirestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser: FirebaseUser? = auth.currentUser
    //when the viewholder is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Loading the child layout to populate the recyclerview
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_recyclerview_item, parent, false)

        db = FirebaseFirestore.getInstance()

        if (firebaseUser != null) {
            val reference = db.collection("users").document(firebaseUser.uid)
            reference.get().addOnSuccessListener { documentSnapshot ->
                user = documentSnapshot.toObject<User>(User::class.java)!!

            }
        }

        return ViewHolder(view)

    }

    //setup each individual recyclerview item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Set plantName Textview to plants name
        holder.plantName.text = plants[position].name
        //Load image as bitmap from image URL to imageView
        Glide.with(mContext)
                .asBitmap()
                .load(plants[position].imageUrl)
                .into(holder.plantImage)
        //check if user is signed in if not hide add to account button
        if (FirebaseAuth.getInstance().currentUser == null) {
            holder.collectionButton.visibility = View.INVISIBLE
        }
        //onclick listener for the addbutton
        holder.collectionButton.setOnClickListener {
            // makes use of position to know which plant from the plants array to add
            val reference = db.collection("users").document(firebaseUser!!.uid)
            reference.get().addOnSuccessListener { documentSnapshot ->
                user = documentSnapshot.toObject<User>(User::class.java)!!
                user.addPlant(plants[position])
            }
        }

        holder.parentLayout.setOnClickListener {
            Toast.makeText(mContext, plants[position].name, Toast.LENGTH_SHORT).show()
            val intent = Intent(mContext, PlantInfoActivity::class.java)
            intent.putExtra("plant", plants[position])
            mContext.startActivity(intent)
        }

        when (plants[position].placement) {
            //int 1 placement outside
            1 -> {
                holder.placementIcon.setImageResource(R.drawable.ic_tree)
            }
            //int 2 placement inside
            2 -> {
                holder.placementIcon.setImageResource(R.drawable.ic_home)
            }
            else -> holder.placementIcon.setImageResource(R.drawable.ic_tree)
        }
        when (plants[position].sun) {
            //int 1 = full on sun
            1 -> {
                holder.sunIcon.setImageResource(R.drawable.ic_sun)
            }
            //int 2 = indirect sunlight
            2 -> {
                holder.sunIcon.setImageResource(R.drawable.ic_cloud_sun)
            }
            //int 3 = shade/dark
            3 -> {
                holder.sunIcon.setImageResource(R.drawable.ic_cloud)
            }
            //default if no info is pressent
            else -> holder.sunIcon.setImageResource(R.drawable.ic_sun)
        }
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var plantImage: CircleImageView = itemView.findViewById(R.id.plantImageView)
        var placementIcon: ImageView = itemView.findViewById(R.id.placementImageView)
        var sunIcon: ImageView = itemView.findViewById(R.id.sunImageView)
        var plantName: TextView = itemView.findViewById(R.id.plantNameTextview)
        var collectionButton: MaterialButton = itemView.findViewById(R.id.collectionButton)
        var parentLayout: CardView = itemView.findViewById(R.id.plantItemCardView)


    }
}




