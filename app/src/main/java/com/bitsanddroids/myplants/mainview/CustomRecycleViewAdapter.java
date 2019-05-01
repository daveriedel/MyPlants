package com.bitsanddroids.myplants.mainview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.infoPages.PlantInfoActivity;
import com.bitsanddroids.myplants.plants.PersonalPlant;
import com.bitsanddroids.myplants.plants.Plant;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomRecycleViewAdapter extends RecyclerView.Adapter<CustomRecycleViewAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Plant> plants;
    private ArrayList<PersonalPlant> personalPlants;


    public CustomRecycleViewAdapter(ArrayList<Plant> plants, Context context) {
        this.mContext = context;
        this.plants = plants;

    }

    //when the viewholder is created
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Loading the child layout to populate the recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_recyclerview_item, parent, false);

        return new ViewHolder(view);

    }
    //setup each individual recyclerview item
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        //Set plantName Textview to plants name
        holder.plantName.setText(plants.get(position).getName());
        //Load image as bitmap from image URL to imageView
        Glide.with(mContext)
                .asBitmap()
                .load(plants.get(position).getImageUrl())
                .into(holder.plantImage);
        //check if user is signed in if not hide add to account button
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            holder.collectionButton.setVisibility(View.INVISIBLE);
        }
        //onclick listener for the addbutton
        holder.collectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // makes use of position to know which plant from the plants array to add
                MainActivity.addPlant(plants.get(position));
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, plants.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, PlantInfoActivity.class);
                intent.putExtra("plant", plants.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView plantImage;
        ImageView placementIcon;
        ImageView sunIcon;
        TextView plantName;
        Button collectionButton;
        ConstraintLayout parentLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            plantImage = itemView.findViewById(R.id.plantImageView);
            placementIcon = itemView.findViewById(R.id.placementImageView);
            sunIcon = itemView.findViewById(R.id.sunImageView);
            parentLayout = itemView.findViewById(R.id.plantItemConstraintLayout);
            plantName = itemView.findViewById(R.id.plantNameTextview);
            collectionButton = itemView.findViewById(R.id.collectionButton);
        }
    }
}




