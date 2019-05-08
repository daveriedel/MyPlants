package com.bitsanddroids.myplants.userauthentication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.bitsanddroids.myplants.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null

    private var usernameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null

    private var registerButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_user_page)

        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        registerButton = findViewById(R.id.registerButton)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        registerButton!!.setOnClickListener { createAccount() }
    }


    private fun createAccount() {
        val email = emailEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val userDB = auth!!.currentUser
                //check if user is really pressent
                if (userDB != null) {
                    //create user object to store personalplants and username
                    val user = User(userDB.uid, usernameEditText!!.text.toString(), null,1)
                    val db = FirebaseFirestore.getInstance()
                    db.collection("users")
                            .document(userDB.uid)
                            .set(user)
                }
            } else {
                Toast.makeText(this@RegisterActivity, "Could not register this account", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
