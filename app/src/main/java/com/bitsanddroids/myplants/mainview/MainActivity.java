package com.bitsanddroids.myplants.mainview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.plants.PersonalPlant;
import com.bitsanddroids.myplants.plants.Plant;
import com.bitsanddroids.myplants.userauthentication.LoginActivity;
import com.bitsanddroids.myplants.userauthentication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Plant> plants;
    private static FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private static User user;
    private static SharedPreferences pref;
    private CustomRecycleViewAdapter adapter;

    private DrawerLayout drawerLayout;
    private ActionBar actionBar;
    private ActionBarDrawerToggle drawerToggle;
    private androidx.appcompat.widget.Toolbar mToolbar;
    private NavigationView navigationView;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelector(menuItem);
                return false;
            }
        });



        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout , R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        plants = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        //load the current logged in user if logged in
        loadUser();

        pref = this.getSharedPreferences(
                "com.bitsanddroids.myplants", Context.MODE_PRIVATE
        );
        //load the data in the reyclerview
        initRecyclerView();




    }

    @Override
    protected void onResume() {
        super.onResume();
        //reload user to make sure changes made to the user class outside of this activity are updated
        loadUser();
    }

    public void login() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void myPlants() {
        Intent intent = new Intent(this, PersonalPlantActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void UserMenuSelector(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.loginMenu:
                login();
                Toast.makeText(this,"login",Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.myAccountMenu:
                break;
            case R.id.myPlants:
                myPlants();
                break;
            case R.id.logoutMenu:
                logOut();
                break;
            case R.id.contactMenu:
                break;


        }
    }

    public void initPlants() {

        db.collection("planten")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Plant plant = document.toObject(Plant.class);
                                plants.add(plant);
                                adapter.notifyDataSetChanged();
                                Log.d("Added", document.toObject(Plant.class).getName() + " was added");
                            }

                        }

                    }
                });

    }
    public void setLanguage(){
        int language = user.getLanguage();
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putInt("LANGUAGE_KEY", language);
        prefEditor.apply();
    }

    public static void addPlant(Plant plant) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            PersonalPlant personalPlant = new PersonalPlant(plant.getName(), plant.getImageUrl(), plant.getSun(), plant.getPlacement(), plant.getToxic(), plant.getWater(), plant.getEdible(), user.getUserId(), plant.getPlantingTime());
            user.addPlant(personalPlant);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference personalPlantsRef = db.collection("users").document(user.getUserId());
            personalPlantsRef.set(user);

        }
    }

    public void initRecyclerView() {

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

    public void loadUser() {
        if (firebaseUser != null) {
            DocumentReference reference = db.collection("users").document(firebaseUser.getUid());
            reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    setLanguage();
                }
            });
        }
    }
}
