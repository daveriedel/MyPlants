package com.bitsanddroids.myplants.mainview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.plants.PersonalPlant;
import com.bitsanddroids.myplants.plants.Plant;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalCustomRecycleViewAdapter extends RecyclerView.Adapter<PersonalCustomRecycleViewAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<PersonalPlant> personalPlants;


    public PersonalCustomRecycleViewAdapter(ArrayList<PersonalPlant> plants, Context context) {
        this.mContext = context;
        this.personalPlants = plants;

    }

    //when the viewholder is created
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Loading the child layout to populate the recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_plant_recyclerview_item, parent, false);

        return new ViewHolder(view);

    }
    //setup each individual recyclerview item
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        //Set plantName Textview to plants name
        holder.plantName.setText(personalPlants.get(position).getName());
        //Load image as bitmap from image URL to imageView
        Glide.with(mContext)
                .asBitmap()
                .load(personalPlants.get(position).getImageUrl())
                .into(holder.plantImage);
        //check if user is signed in if not hide add to account button
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return personalPlants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView plantImage;
        ImageView placementIcon;
        ImageView sunIcon;
        TextView plantName;
        Button deleteButton;
        ConstraintLayout parentLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            plantImage = itemView.findViewById(R.id.personalPlantImageView);
            placementIcon = itemView.findViewById(R.id.placementImageView);
            sunIcon = itemView.findViewById(R.id.sunImageView);
            parentLayout = itemView.findViewById(R.id.personalPlantConstraintLayout);
            plantName = itemView.findViewById(R.id.personalPlantNameTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}




