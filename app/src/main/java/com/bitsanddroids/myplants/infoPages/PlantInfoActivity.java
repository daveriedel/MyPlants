package com.bitsanddroids.myplants.infoPages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.plants.Plant;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlantInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_overview);

        Intent intent = getIntent();
        Plant plant = (Plant) intent.getExtras().getSerializable("plant");

        CircleImageView circleImageView = findViewById(R.id.plantInfoImageView);
        TextView plantName = findViewById(R.id.nameInfoTextView);

        ImageView sunImage = findViewById(R.id.sunIconImageView);
        TextView sunText = findViewById(R.id.sunTextView);

        ImageView placementImage = findViewById(R.id.placementImageView);
        TextView placementText = findViewById(R.id.placementTextView);

        ImageView toxicImage = findViewById(R.id.toxicImageView);
        TextView toxicText = findViewById(R.id.toxicTextView);

        ImageView waterImage = findViewById(R.id.waterImageView);
        TextView waterText = findViewById(R.id.waterTextView);

        ImageView edibleImageView = findViewById(R.id.edibleImageView);
        TextView edibleText = findViewById(R.id.edibleTextView);

        ImageView noteImage = findViewById(R.id.customNoteImageView);
        TextView noteText = findViewById(R.id.customNoteTextView);

        Button watteredButton = findViewById(R.id.waterButton);
        TextView waterDateText = findViewById(R.id.wateredTextView);

        Glide.with(this)
                .asBitmap()
                .load(plant.getImageUrl())
                .into(circleImageView);

        plantName.setText(plant.getName());

        /*get sun int from plant object and display corresponding image resource
         * sets the corresponding string*/
        switch (plant.getSun()) {
            //int 1 = full on sun
            case 1:
                sunImage.setImageResource(R.drawable.ic_sun);
                break;
            //int 2 = indirect sunlight
            case 2:
                sunImage.setImageResource(R.drawable.ic_cloud_sun);
                break;
            //int 3 = shade/dark
            case 3:
                sunImage.setImageResource(R.drawable.ic_cloud);
                break;
            //default if no info is pressent
            default:
                sunImage.setImageResource(R.drawable.ic_sun);
                break;

        }
        /*get Placement int from plant object and display corresponding image resource
         * sets the corresponding string*/
        switch (plant.getPlacement()) {
            //int 1 placement outside
            case 1:
                placementImage.setImageResource(R.drawable.ic_tree);
                break;
            //int 2 placement inside
            case 2:
                placementImage.setImageResource(R.drawable.ic_home);
                break;
            default:
                placementImage.setImageResource(R.drawable.ic_tree);
        }

        /*get toxic int from plant object and display corresponding image resource
         * sets the corresponding string*/
        switch (plant.getToxic()) {
            // 1 = non toxic
            case 1:
                toxicImage.setImageResource(R.drawable.ic_apple_alt);
                break;
            // 2 = toxic vers 1
            case 2:
                toxicImage.setImageResource(R.drawable.ic_skull_crossbones);
                break;
            // 3 = toxic vers 2
            case 3:
                toxicImage.setImageResource(R.drawable.ic_skull_crossbones);
                break;
            default:
                toxicImage.setImageResource(R.drawable.ic_skull_crossbones);
                break;
        }
        /*get watering int from plant object and display corresponding image resource
         * sets the corresponding string*/
        switch (plant.getWater()){
            // 1 = keep moist
            case 1:
                waterImage.setImageResource(R.drawable.ic_water);
                break;
            // 2 = water when soil is starting to dry
            case 2:
                waterImage.setImageResource(R.drawable.ic_tint);
                break;
            // 3 = water when soil is completely dry
            case 3:
                waterImage.setImageResource(R.drawable.ic_tint_slash);
                break;
                default:
                    waterImage.setImageResource(R.drawable.ic_water);
                    break;

        }
        /*get edible int from plant object and display corresponding image resource
         * sets the corresponding string
         * The reason we use an int instead of a Boolean is to keep exotic options open like
         * only fruit is edible or plant is entirely edible*/
        switch (plant.getEdible()){
            // 1 = edible
            case 1:
                edibleImageView.setImageResource(R.drawable.ic_utensils);
                break;
            // 2 = toxic
            case 2:
                edibleImageView.setImageResource(R.drawable.ic_book_dead);
                break;
                default:
                    edibleImageView.setImageResource(R.drawable.ic_book_dead);
        }


        // note image is constant
        noteImage.setImageResource(R.drawable.ic_sticky_note);


    }
}
