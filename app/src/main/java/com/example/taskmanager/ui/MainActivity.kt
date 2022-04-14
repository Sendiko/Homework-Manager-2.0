package com.example.taskmanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanager.R
import com.example.taskmanager.database.InputActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_add.setOnClickListener{
            intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
    }
}