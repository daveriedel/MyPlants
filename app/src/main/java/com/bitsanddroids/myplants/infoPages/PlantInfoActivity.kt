package com.bitsanddroids.myplants.infoPages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.bitsanddroids.myplants.R
import com.bitsanddroids.myplants.plants.Plant
import com.bumptech.glide.Glide

import java.util.Objects

import de.hdodenhof.circleimageview.CircleImageView

class PlantInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plant_overview)

        val intent = intent
        val plant = Objects.requireNonNull(intent.extras).getSerializable("plant") as Plant


        val circleImageView = findViewById<CircleImageView>(R.id.plantInfoImageView)
        val plantName = findViewById<TextView>(R.id.nameInfoTextView)

        val watteredButton = findViewById<Button>(R.id.waterButton)
        val waterDateText = findViewById<TextView>(R.id.wateredTextView)

        Glide.with(this)
                .asBitmap()
                .load(plant.imageUrl)
                .into(circleImageView)

        plantName.text = plant.name


        val sunImage = findViewById<ImageView>(R.id.sunIconImageView)
        val sunText = findViewById<TextView>(R.id.sunTextView)

        /*get sun int from plant object and display corresponding image resource
         * sets the corresponding string*/
        when (plant.sun) {
            //int 1 = full on sun
            1 -> {
                sunImage.setImageResource(R.drawable.ic_sun)
                sunText.setText(R.string.sun1)
            }
            //int 2 = indirect sunlight
            2 -> {
                sunImage.setImageResource(R.drawable.ic_cloud_sun)
                sunText.setText(R.string.sun2)
            }
            //int 3 = shade/dark
            3 -> {
                sunImage.setImageResource(R.drawable.ic_cloud)
                sunText.setText(R.string.sun3)
            }
            //default if no info is pressent
            else -> sunImage.setImageResource(R.drawable.ic_sun)
        }
        val placementImage = findViewById<ImageView>(R.id.placementImageView)
        val placementText = findViewById<TextView>(R.id.placementTextView)

        /*get Placement int from plant object and display corresponding image resource
         * sets the corresponding string*/
        when (plant.placement) {
            //int 1 placement outside
            1 -> {
                placementImage.setImageResource(R.drawable.ic_tree)
                placementText.setText(R.string.placement1)
            }
            //int 2 placement inside
            2 -> {
                placementImage.setImageResource(R.drawable.ic_home)
                placementText.setText(R.string.placement2)
            }
            else -> placementImage.setImageResource(R.drawable.ic_tree)
        }
        val toxicImage = findViewById<ImageView>(R.id.toxicImageView)
        val toxicText = findViewById<TextView>(R.id.toxicTextView)

        /*get toxic int from plant object and display corresponding image resource
         * sets the corresponding string*/
        when (plant.toxic) {
            // 1 = non toxic
            1 -> {
                toxicImage.setImageResource(R.drawable.ic_apple_alt)
                toxicText.setText(R.string.toxic1)
            }
            // 2 = toxic vers 1
            2 -> {
                toxicImage.setImageResource(R.drawable.ic_skull_crossbones)
                toxicText.setText(R.string.toxic2)
            }
            // 3 = toxic vers 2
            3 -> {
                toxicImage.setImageResource(R.drawable.ic_skull_crossbones)
                toxicText.setText(R.string.toxic3)
            }
            else -> toxicImage.setImageResource(R.drawable.ic_skull_crossbones)
        }

        val waterImage = findViewById<ImageView>(R.id.waterImageView)
        val waterText = findViewById<TextView>(R.id.waterTextView)

        /*get watering int from plant object and display corresponding image resource
         * sets the corresponding string*/
        when (plant.water) {
            // 1 = keep moist
            1 -> {
                waterImage.setImageResource(R.drawable.ic_water)
                waterText.setText(R.string.water1)
            }
            // 2 = water when soil is starting to dry
            2 -> {
                waterImage.setImageResource(R.drawable.ic_tint)
                waterText.setText(R.string.water2)
            }
            // 3 = water when soil is completely dry
            3 -> {
                waterImage.setImageResource(R.drawable.ic_tint_slash)
                waterText.setText(R.string.water3)
            }
            else -> waterImage.setImageResource(R.drawable.ic_water)
        }

        val edibleImageView = findViewById<ImageView>(R.id.edibleImageView)
        val edibleText = findViewById<TextView>(R.id.edibleTextView)

        /*get edible int from plant object and display corresponding image resource
         * sets the corresponding string
         * The reason we use an int instead of a Boolean is to keep exotic options open like
         * only fruit is edible or plant is entirely edible*/
        when (plant.edible) {
            // 1 = edible
            1 -> {
                edibleImageView.setImageResource(R.drawable.ic_utensils)
                edibleText.setText(R.string.edible1)
            }
            2 -> {
                edibleImageView.setImageResource(R.drawable.ic_utensils)
                edibleText.setText(R.string.edible2)
            }
            // 2 = toxic
            3 -> {
                edibleImageView.setImageResource(R.drawable.ic_book_dead)
                edibleText.setText(R.string.edible3)
            }
            else -> edibleImageView.setImageResource(R.drawable.ic_book_dead)
        }

        val sowingImageView = findViewById<ImageView>(R.id.sowingImageView)
        val sowingText = findViewById<TextView>(R.id.sowingTextView)

        sowingImageView.setImageResource(R.drawable.ic_seedling)
/*        if (plant.plantingTime != null) {
             languages are split by @ in db. starting from the second language
            * Example NL:... @EN:.....@DE....
            val languages = plant.plantingTime.split("@", 3)

            //if there is info pressent
            if (languages.size > 0) {
                //first entry is dutch
                val dutch = languages[0]
                //second entry is english
                val english = languages[1]
                replace _nl (new line) with \n !--substring 4 is applied to remove the language
                * marker present in the database
                sowingText.setText(dutch.replace("_nl", "\n").substring(4))
            }


        } else {
            //if no info is available show this text
            sowingText.setText(R.string.plantingTime1)
        }*/

        val noteImage = findViewById<ImageView>(R.id.customNoteImageView)
        val noteText = findViewById<TextView>(R.id.customNoteTextView)
        // note image is constant
        noteImage.setImageResource(R.drawable.ic_sticky_note)


    }
}
