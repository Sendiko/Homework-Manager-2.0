package com.example.taskmanager.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmanager.R
import com.example.taskmanager.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    // TODO : FIREBASE AUTHENTICATION
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // TODO : FIREBASE AUTHENTICATION
        auth = Firebase.auth

        setupListener()
    }

    // TODO : DISABLE BACK NAVIGATION
    override fun onBackPressed() {
        Toast.makeText(this, "Use home button to exit the app", Toast.LENGTH_SHORT).show()
    }

    // TODO : SETUP LISTENER
    private fun setupListener() {
        button_login.setOnClickListener{
            login()
        }
        text_register.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // TODO : LOGIN WITH EXISTING ACCOUNT
    private fun login() {
        val email = email_login.text.toString()
        val pass = password_login.text.toString()

        if(email.isBlank() || pass.isBlank()){
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            email_login.setText("")
            password_login.setText("")
        }else{
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show()
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                    email_login.setText("")
                    password_login.setText("")
                }
            }
        }

    }
}