package com.bitsanddroids.myplants.infoPages;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.plants.Plant;
import com.bitsanddroids.myplants.userauthentication.User;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.Arrays;

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

        Button watteredButton = findViewById(R.id.waterButton);
        TextView waterDateText = findViewById(R.id.wateredTextView);

        Glide.with(this)
                .asBitmap()
                .load(plant.getImageUrl())
                .into(circleImageView);

        plantName.setText(plant.getName());



        ImageView sunImage = findViewById(R.id.sunIconImageView);
        TextView sunText = findViewById(R.id.sunTextView);

        /*get sun int from plant object and display corresponding image resource
         * sets the corresponding string*/
        switch (plant.getSun()) {
            //int 1 = full on sun
            case 1:
                sunImage.setImageResource(R.drawable.ic_sun);
                sunText.setText(R.string.sun1);
                break;
            //int 2 = indirect sunlight
            case 2:
                sunImage.setImageResource(R.drawable.ic_cloud_sun);
                sunText.setText(R.string.sun2);
                break;
            //int 3 = shade/dark
            case 3:
                sunImage.setImageResource(R.drawable.ic_cloud);
                sunText.setText(R.string.sun3);
                break;
            //default if no info is pressent
            default:
                sunImage.setImageResource(R.drawable.ic_sun);
                break;

        }
        ImageView placementImage = findViewById(R.id.placementImageView);
        TextView placementText = findViewById(R.id.placementTextView);

        /*get Placement int from plant object and display corresponding image resource
         * sets the corresponding string*/
        switch (plant.getPlacement()) {
            //int 1 placement outside
            case 1:
                placementImage.setImageResource(R.drawable.ic_tree);
                placementText.setText(R.string.placement1);
                break;
            //int 2 placement inside
            case 2:
                placementImage.setImageResource(R.drawable.ic_home);
                placementText.setText(R.string.placement2);
                break;
            default:
                placementImage.setImageResource(R.drawable.ic_tree);
                break;
        }
        ImageView toxicImage = findViewById(R.id.toxicImageView);
        TextView toxicText = findViewById(R.id.toxicTextView);

        /*get toxic int from plant object and display corresponding image resource
         * sets the corresponding string*/
        switch (plant.getToxic()) {
            // 1 = non toxic
            case 1:
                toxicImage.setImageResource(R.drawable.ic_apple_alt);
                toxicText.setText(R.string.toxic1);
                break;
            // 2 = toxic vers 1
            case 2:
                toxicImage.setImageResource(R.drawable.ic_skull_crossbones);
                toxicText.setText(R.string.toxic2);
                break;
            // 3 = toxic vers 2
            case 3:
                toxicImage.setImageResource(R.drawable.ic_skull_crossbones);
                toxicText.setText(R.string.toxic3);
                break;
            default:
                toxicImage.setImageResource(R.drawable.ic_skull_crossbones);
                break;
        }

        ImageView waterImage = findViewById(R.id.waterImageView);
        TextView waterText = findViewById(R.id.waterTextView);

        /*get watering int from plant object and display corresponding image resource
         * sets the corresponding string*/
        switch (plant.getWater()) {
            // 1 = keep moist
            case 1:
                waterImage.setImageResource(R.drawable.ic_water);
                waterText.setText(R.string.water1);
                break;
            // 2 = water when soil is starting to dry
            case 2:
                waterImage.setImageResource(R.drawable.ic_tint);
                waterText.setText(R.string.water2);
                break;
            // 3 = water when soil is completely dry
            case 3:
                waterImage.setImageResource(R.drawable.ic_tint_slash);
                waterText.setText(R.string.water3);
                break;
            default:
                waterImage.setImageResource(R.drawable.ic_water);
                break;

        }

        ImageView edibleImageView = findViewById(R.id.edibleImageView);
        TextView edibleText = findViewById(R.id.edibleTextView);

        /*get edible int from plant object and display corresponding image resource
         * sets the corresponding string
         * The reason we use an int instead of a Boolean is to keep exotic options open like
         * only fruit is edible or plant is entirely edible*/
        switch (plant.getEdible()) {
            // 1 = edible
            case 1:
                edibleImageView.setImageResource(R.drawable.ic_utensils);
                edibleText.setText(R.string.edible1);
                break;
            case 2:
                edibleImageView.setImageResource(R.drawable.ic_utensils);
                edibleText.setText(R.string.edible2);
                break;
            // 2 = toxic
            case 3:
                edibleImageView.setImageResource(R.drawable.ic_book_dead);
                edibleText.setText(R.string.edible3);
                break;
            default:
                edibleImageView.setImageResource(R.drawable.ic_book_dead);
                break;
        }

        ImageView sowingImageView = findViewById(R.id.sowingImageView);
        TextView sowingText = findViewById(R.id.sowingTextView);

        sowingImageView.setImageResource(R.drawable.ic_seedling);
        if (plant.getPlantingTime() != null){
            /* languages are split by @ in db. starting from the second language
            * Example NL:... @EN:.....@DE....*/
            String[] languages = plant.getPlantingTime().split("@",3);

            //if there is info pressent
            if(languages.length > 0) {
                //first entry is dutch
                String dutch = languages[0];
                //second entry is english
                String english = languages[1];
                /*replace _nl (new line) with \n !--substring 4 is applied to remove the language
                * marker present in the database*/
                sowingText.setText(dutch.replace("_nl","\n").substring(4));
            }


        }else {
            //if no info is availlable show this text
            sowingText.setText(R.string.plantingTime1);
        }

        ImageView noteImage = findViewById(R.id.customNoteImageView);
        TextView noteText = findViewById(R.id.customNoteTextView);
        // note image is constant
        noteImage.setImageResource(R.drawable.ic_sticky_note);


    }
}
