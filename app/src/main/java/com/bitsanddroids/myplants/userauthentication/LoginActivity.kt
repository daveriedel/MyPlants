package com.bitsanddroids.myplants.userauthentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.bitsanddroids.myplants.R
import com.bitsanddroids.myplants.mainview.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : Activity() {

    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var loginButton: Button? = null
    private var registerTextView: TextView? = null

    private var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * 0.8).toInt(), (height * .4).toInt())
        val params = window.attributes
        params.gravity = Gravity.CENTER
        params.x = 0
        params.y = -20

        window.attributes = params

        auth = FirebaseAuth.getInstance()

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        loginButton = findViewById(R.id.loginButton)

        registerTextView = findViewById(R.id.registerLink)

        loginButton!!.setOnClickListener { login() }

        registerTextView!!.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    fun login() {
        val intent = Intent(this, MainActivity::class.java)
        auth!!.signInWithEmailAndPassword(usernameEditText!!.text.toString(), passwordEditText!!.text.toString()).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth!!.currentUser
                startActivity(intent)


            } else {
                Toast.makeText(this@LoginActivity, "Could not log you in", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

