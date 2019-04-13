package com.bitsanddroids.myplants.mainview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bitsanddroids.myplants.R;
import com.bitsanddroids.myplants.plants.Plant;
import com.bitsanddroids.myplants.userauthentication.LoginActivity;
import com.bitsanddroids.myplants.userauthentication.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, Plant> plantHashMap;

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

            case R.id.logoutMenu:

            case R.id.contactMenu:


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void initPlants(){
        for(Map.Entry<String, Plant> plant : plantHashMap.entrySet()){

        }
    }
}
