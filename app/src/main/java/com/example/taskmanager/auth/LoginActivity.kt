package com.example.taskmanager.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmanager.R
import com.example.taskmanager.database.InputActivity
import com.example.taskmanager.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        button_login.setOnClickListener{
            login()
        }
        text_register.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = email_login.text.toString()
        val pass = password_login.text.toString()

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