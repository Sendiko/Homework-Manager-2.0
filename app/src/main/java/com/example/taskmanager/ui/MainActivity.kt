package com.example.taskmanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.auth.LoginActivity
import com.example.taskmanager.database.InputActivity
import com.example.taskmanager.database.RVTaskAdapter
import com.example.taskmanager.database.Taskk
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // TODO : FIREBASE
    private lateinit var db : DatabaseReference
    private lateinit var auth : FirebaseAuth
    private lateinit var taskRV : RecyclerView
    private lateinit var taskArrayList : ArrayList<Taskk>

    // TODO : FAB ANIMATION
    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom) }
    private var clicked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        recyclerView()
    }

    // TODO : SETUP RECYCLERVIEW
    private fun recyclerView() {
        taskRV = rv_task
        taskRV.layoutManager = LinearLayoutManager(this)
        taskRV.setHasFixedSize(true)
        taskArrayList = arrayListOf()
        getTaskData()
    }

    // TODO : SETUP RECYCLERVIEW
    private fun getTaskData() {
        auth = Firebase.auth
        val user = auth.currentUser
        db = FirebaseDatabase.getInstance().getReference(user?.uid.toString())
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    taskArrayList.clear()
                    for(taskSnapshot in snapshot.children){
                        val task = taskSnapshot.getValue(Taskk::class.java)
                        taskArrayList.add(task!!)
                    }
                    taskRV.adapter = RVTaskAdapter(taskArrayList, this@MainActivity)
                    text_empty.visibility = View.GONE
                }else{
                    text_empty.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("imma find something to put here")
            }

        })
    }

    // TODO : DISABLE BACK BUTTON
    override fun onBackPressed() {
        Toast.makeText(this, "Use home button to exit", Toast.LENGTH_SHORT).show()
    }

    // TODO : SETUP LISTENER
    private fun setupListener(){
        button_menu.setOnClickListener{
            floatingActionAnimation()
        }
        button_add.setOnClickListener {
            intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
        button_logout.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        text_empty.setOnClickListener {
            intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
    }

    //TODO : FAB ANIMATION
    private fun floatingActionAnimation() {
        setVisibility(clicked)
        startAnimation(clicked)
        clicked = !clicked
    }

    //TODO : FAB ANIMATION
    private fun startAnimation(clicked : Boolean) {
        if (!clicked){
            button_logout.startAnimation(toBottom)
            button_add.startAnimation(toBottom)
            button_menu.startAnimation(rotateClose)
        }else{
            button_logout.startAnimation(fromBottom)
            button_add.startAnimation(fromBottom)
            button_menu.startAnimation(rotateOpen)
        }
    }

    //TODO : FAB ANIMATION
    private fun setVisibility(clicked : Boolean) {
        if (!clicked){
            button_logout.visibility = View.GONE
            button_add.visibility = View.GONE
        }else{
            button_logout.visibility = View.VISIBLE
            button_add.visibility = View.VISIBLE
        }
    }
}
