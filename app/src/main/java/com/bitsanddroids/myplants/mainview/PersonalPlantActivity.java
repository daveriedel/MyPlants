package com.bitsanddroids.myplants.mainview;

import android.os.Bundle;
import android.util.Log;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.plants.PersonalPlant;
import com.bitsanddroids.myplants.plants.Plant;
import com.bitsanddroids.myplants.userauthentication.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PersonalPlantActivity extends AppCompatActivity {
    private ArrayList<PersonalPlant> personalPlants = new ArrayList<>();
    private static FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private static User user;

    private PersonalCustomRecycleViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        initRecyclerView();
    }

    public void initPlants() {

            DocumentReference docRef = db.collection("users").document(auth.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    personalPlants.addAll(user.getPersonalPlants());
                    Log.d("DEBUG", "loaded user " + user.getUsername());
                    Log.d("DEBUG", "loaded plant " + user.getPersonalPlants().get(1).getName());

                    adapter.notifyDataSetChanged();
                }
            });



    }

    public void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new PersonalCustomRecycleViewAdapter(personalPlants, PersonalPlantActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initPlants();
    }
}
