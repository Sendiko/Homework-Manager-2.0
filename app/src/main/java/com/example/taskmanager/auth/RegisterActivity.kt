package com.example.taskmanager.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmanager.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        button_register.setOnClickListener {
            register()
        }
        text_login.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register() {
        val emailreg = email_register.text.toString()
        val passreg = password_register.text.toString()
        val confpass = confirm_password_register.text.toString()

        if (emailreg.isBlank() || passreg.isBlank()){
            Toast.makeText(this, "Please insert Email and Password", Toast.LENGTH_SHORT).show()
            return
        }

        if (passreg != confpass){
            Toast.makeText(this, "Password not matched", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(emailreg, passreg).addOnCompleteListener(this) {
            if (it.isSuccessful){
                Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show()
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}