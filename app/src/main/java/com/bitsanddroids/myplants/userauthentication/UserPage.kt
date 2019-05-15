package com.bitsanddroids.myplants.userauthentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bitsanddroids.myplants.GlideApp
import com.bitsanddroids.myplants.R
import com.bitsanddroids.myplants.mainview.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView

class UserPage : AppCompatActivity() {

    private lateinit var userName: TextView
    private lateinit var editProfilePicture: ImageButton
    lateinit var storage: FirebaseStorage

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_page)

        storage = FirebaseStorage.getInstance()

        val pfPictureImageView = findViewById<CircleImageView>(R.id.CircleImageView_pfpicture)

        userName = findViewById(R.id.textview_username)
        editProfilePicture = findViewById(R.id.imagebutton_eddit_pfpicture)

        userName.text = MainActivity.user.username

        editProfilePicture.setOnClickListener {
            selectImage()
        }

        val storageRef = storage.reference.child("profile images").child(MainActivity.user.userId + ".jpg")


        GlideApp.with(applicationContext)
                .asBitmap()
                .load(storageRef)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(pfPictureImageView)


    }

    fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val profilePicture: CircleImageView = findViewById(R.id.CircleImageView_pfpicture)
            profilePicture.setImageURI(data?.data)
            val imageUri = data!!.data

            val userProfilePicture: StorageReference = FirebaseStorage.getInstance().reference.child("profile images")

            val filePath: StorageReference = userProfilePicture.child(MainActivity.user.userId + ".jpg")
            val uploadTask = filePath.putFile(imageUri!!)
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@continueWithTask filePath.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "profile picture is uploaded", Toast.LENGTH_SHORT).show()
                } else {

                }

            }
        }
    }


}