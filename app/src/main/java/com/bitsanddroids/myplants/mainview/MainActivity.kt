package com.bitsanddroids.myplants.mainview

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast

import com.bitsanddroids.myplants.R
import com.bitsanddroids.myplants.plants.PersonalPlant
import com.bitsanddroids.myplants.plants.Plant
import com.bitsanddroids.myplants.userauthentication.LoginActivity
import com.bitsanddroids.myplants.userauthentication.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var plants: ArrayList<Plant>
    lateinit var firebaseUser: FirebaseUser
    lateinit var pref: SharedPreferences
    private var auth: FirebaseAuth? = null
    lateinit var db: FirebaseFirestore
    private var adapter: CustomRecycleViewAdapter? = null
    lateinit var user: User
    private var drawerLayout: DrawerLayout? = null
    private val actionBar: ActionBar? = null
    private var drawerToggle: ActionBarDrawerToggle? = null
    private var mToolbar: androidx.appcompat.widget.Toolbar? = null
    private var navigationView: NavigationView? = null


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (drawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)

        navigationView = findViewById(R.id.navigation_view)

        navigationView!!.setNavigationItemSelectedListener { menuItem ->
            UserMenuSelector(menuItem)
            false
        }



        mToolbar = findViewById(R.id.main_page_toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.title = "Home"

        drawerToggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout!!.addDrawerListener(drawerToggle!!)
        drawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        plants = ArrayList()

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth!!.currentUser!!
        db = FirebaseFirestore.getInstance()

        //load the current logged in user if logged in
        loadUser()

        pref = this.getSharedPreferences(
                "com.bitsanddroids.myplants", Context.MODE_PRIVATE
        )
        //load the data in the reyclerview
        initRecyclerView()


    }

    override fun onResume() {
        super.onResume()
        //reload user to make sure changes made to the user class outside of this activity are updated
        loadUser()
    }

    fun login() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }

    fun myPlants() {
        val intent = Intent(this, PersonalPlantActivity::class.java)
        //intent.putExtra("user", user)
        startActivity(intent)
    }

    private fun UserMenuSelector(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.loginMenu -> {
                login()
                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
            R.id.myAccountMenu -> {
            }
            R.id.myPlants -> myPlants()
            R.id.logoutMenu -> logOut()
            R.id.contactMenu -> {
            }
        }
    }

    fun initPlants() {

        db.collection("planten")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            val plant = document.toObject<Plant>(Plant::class.java)
                            plants.add(plant)
                            adapter!!.notifyDataSetChanged()
                            Log.d("Added", document.toObject<Plant>(Plant::class.java).name + " was added")
                        }

                    }
                }

    }

    fun setLanguage() {
        val language = user.language
        val prefEditor = pref!!.edit()
        prefEditor.putInt("LANGUAGE_KEY", language)
        prefEditor.apply()
    }

    fun initRecyclerView() {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //initializing the custom recyclerview adapter
        adapter = CustomRecycleViewAdapter(plants, this)
        //adding the adapter to the recyclerview
        recyclerView.adapter = adapter
        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this)
        //!!CALL AFTER the recyclerviewInit. If not done this way plantInit will try to update the adapter before the adapter is ready!!
        initPlants()

    }

    fun loadUser() {
        if (firebaseUser != null) {
            val reference = db.collection("users").document(firebaseUser!!.uid)
            reference.get().addOnSuccessListener { documentSnapshot ->
                user = documentSnapshot.toObject<User>(User::class.java)!!
                setLanguage()
            }
        }
    }


}
