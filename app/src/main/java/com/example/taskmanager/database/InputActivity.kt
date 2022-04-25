package com.example.taskmanager.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmanager.R
import com.example.taskmanager.ui.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : AppCompatActivity() {

    // TODO : DATABASE REFERENCE
    private lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        // TODO : BACK BUTTON IN ACTION BAR
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // TODO : FIREBASE DATABASE
        ref = FirebaseDatabase.getInstance().getReference("TASK")

        setupListener()
    }

    // TODO : SETUP LISTENER
    private fun setupListener(){
        button_save.setOnClickListener {
            saveData()
        }
    }

    // TODO : SAVE DATA TO FIREBASE DATABASE
    private fun saveData() {
        val task = name_task.text.toString()
        val sub = name_subject.text.toString()
        val taskID = ref.push().key.toString()
        val tasks = Taskk(taskID, task, sub)

        if (task.isBlank() || sub.isBlank()) {
            Toast.makeText(this, "Please fill in the blank", Toast.LENGTH_SHORT).show()
        } else {
            ref.child(taskID).setValue(tasks).addOnCompleteListener {
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                name_task.setText("")
                name_subject.setText("")
            }
        }
    }
}