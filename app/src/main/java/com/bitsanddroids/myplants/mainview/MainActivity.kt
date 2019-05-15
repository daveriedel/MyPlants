package com.bitsanddroids.myplants.mainview


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bitsanddroids.myplants.GlideApp
import com.bitsanddroids.myplants.R
import com.bitsanddroids.myplants.plants.Plant
import com.bitsanddroids.myplants.userauthentication.LoginActivity
import com.bitsanddroids.myplants.userauthentication.User
import com.bitsanddroids.myplants.userauthentication.UserPage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var plants: ArrayList<Plant>
    var firebaseUser: FirebaseUser? = null
    lateinit var pref: SharedPreferences
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    private var adapter: CustomRecycleViewAdapter? = null

    private var drawerLayout: DrawerLayout? = null
    private var drawerToggle: ActionBarDrawerToggle? = null
    private var mToolbar: androidx.appcompat.widget.Toolbar? = null
    private var navigationView: NavigationView? = null

    companion object {
        lateinit var user: User
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (drawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //load the current logged in user if logged in


        auth = FirebaseAuth.getInstance()

        firebaseUser = auth.currentUser
        db = FirebaseFirestore.getInstance()

        loadUser()

        drawerLayout = findViewById(R.id.drawerLayout)

        navigationView = findViewById(R.id.navigation_view)
        if (auth.currentUser == null) {
            navigationView!!.inflateMenu(R.menu.menu_login)
        } else {
            navigationView!!.inflateMenu(R.menu.main_menu)
        }

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

    private fun login() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun logOut() {
        auth.signOut()
        finish()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    private fun myPlants() {
        val intent = Intent(this, PersonalPlantActivity::class.java)
        //intent.putExtra("user", user)
        startActivity(intent)
    }

    private fun UserMenuSelector(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.menu_login -> {
                login()
                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
            R.id.menu_register -> {
                login()
                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
                drawerLayout!!.closeDrawer(GravityCompat.START)
            }
            R.id.myAccountMenu -> userPage()
            R.id.myPlants -> myPlants()
            R.id.logoutMenu -> logOut()
            R.id.contactMenu -> {
            }
        }
    }

    private fun userPage() {
        val intent = Intent(applicationContext, UserPage::class.java)
        startActivity(intent)
    }

    private fun initPlants() {

        db.collection("planten")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val plant = document.toObject<Plant>(Plant::class.java)
                            plants.add(plant)
                            adapter!!.notifyDataSetChanged()
                            Log.d("Added", document.toObject<Plant>(Plant::class.java).name + " was added")
                        }

                    }
                }

    }

    private fun setLanguage() {
        val language = user.language
        val prefEditor = pref.edit()
        prefEditor.putInt("LANGUAGE_KEY", language)
        prefEditor.apply()
    }

    private fun initRecyclerView() {

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

    private fun loadUser() {
        firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            val reference = db.collection("users").document(firebaseUser!!.uid)
            reference.get().addOnSuccessListener { documentSnapshot ->
                user = documentSnapshot.toObject<User>(User::class.java)!!
                setLanguage()

                val view: View = navigationView!!.getHeaderView(0)
                val storage = FirebaseStorage.getInstance()
                val profilePicture: CircleImageView = view.findViewById(R.id.header_profile_pic)
                val storageRef = storage.reference.child("profile images").child(MainActivity.user.userId + ".jpg")
                GlideApp.with(applicationContext)
                        .asBitmap()
                        .load(storageRef)
                        .apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(profilePicture)
                val username: TextView = view.findViewById(R.id.header_textview_username)
                username.text = user.username

            }

        }

    }


}
