package com.bitsanddroids.myplants.mainview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.plants.Plant;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecycleViewAdapter extends RecyclerView.Adapter<CustomRecycleViewAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Plant> plants;
    HashMap<String, Plant> plantsMap;

    public CustomRecycleViewAdapter(ArrayList<Plant> plants, ArrayList<String> plantId, Context context) {
        this.mContext = context;
        this.plants = plants;

    }

    @NonNull
    @Override
    public CustomRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecycleViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return plantsMap.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView plantImage;
        ImageView placementIcon;
        ImageView sunIcon;
        TextView plantName;
        Button collectionButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plantImage = itemView.findViewById(R.id.plantImageView);
            placementIcon = itemView.findViewById(R.id.placementImageView);
            sunIcon = itemView.findViewById(R.id.sunImageView);
            plantName = itemView.findViewById(R.id.plantNameTextview);
            collectionButton = itemView.findViewById(R.id.collectionButton);
        }
    }
}




