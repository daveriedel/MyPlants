package com.bitsanddroids.myplants.mainview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.plants.PersonalPlant;
import com.bitsanddroids.myplants.plants.Plant;
import com.bitsanddroids.myplants.userauthentication.LoginActivity;
import com.bitsanddroids.myplants.userauthentication.RegisterActivity;
import com.bitsanddroids.myplants.userauthentication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.firestore.FieldValue.arrayUnion;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Plant> plants;
    private static FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private static User user;
    private CustomRecycleViewAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.loginMenu:
                login();
                break;
            case R.id.myAccountMenu:

            case R.id.myPlants:
                myPlants();
                break;
            case R.id.logoutMenu:
                logOut();
                break;
            case R.id.contactMenu:


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plants = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        //load the current logged in user if logged in
        loadUser();
        //load the data in the reyclerview
        initRecyclerView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //reload user to make sure changes made to the user class outside of this activity are updated
        loadUser();
    }

    public void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void logOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public void myPlants(){
        Intent intent = new Intent(this, PersonalPlantActivity.class);
        startActivity(intent);
    }

    public void initPlants(){

        db.collection("planten")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Plant plant = document.toObject(Plant.class);
                                plants.add(plant);
                                adapter.notifyDataSetChanged();
                                Log.d("Added", document.toObject(Plant.class).getName() + " was added");
                            }

                        }

                    }
                });

    }

    public static void addPlant(Plant plant){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            PersonalPlant personalPlant = new PersonalPlant(plant.getName(), plant.getImageUrl(), plant.getSun(), plant.getPlacement(), plant.getToxic(), plant.getWater(), plant.getEdible(), user.getUserId());
            user.addPlant(personalPlant);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference personalPlantsRef = db.collection("users").document(user.getUserId());
            personalPlantsRef.set(user);

        }
    }

    public void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //initializing the custom recyclerview adapter
        adapter = new CustomRecycleViewAdapter(plants, this);
        //adding the adapter to the recyclerview
        recyclerView.setAdapter(adapter);
        //adding a layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //!!CALL AFTER the recyclerviewInit. If not done this way plantInit will try to update the adapter before the adapter is ready!!
        initPlants();

    }

    public void loadUser(){
        if(firebaseUser != null) {
            DocumentReference reference = db.collection("users").document(firebaseUser.getUid());
            reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                }
            });
        }
    }
}
