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
    private static ArrayList<PersonalPlant> personalPlants = new ArrayList<>();
    private static FirebaseUser firebaseUser;
    private static FirebaseAuth auth;
    private static FirebaseFirestore db;
    private static User user;

    private static PersonalCustomRecycleViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        initRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void initPlants() {

        DocumentReference docRef = db.collection("users").document(auth.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                personalPlants.clear();
                personalPlants.addAll(user.getPersonalPlants());
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

    public static void deletePlant(final int position) {

        final DocumentReference ref = db.collection("users").document(auth.getCurrentUser().getUid());

        if (user != null) {

            personalPlants.remove(position);
            Log.d("INDEX", Integer.toString(position));
            user.setPersonalPlants(personalPlants);

            ref.set(user);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, adapter.getItemCount());



        }


    }
}
