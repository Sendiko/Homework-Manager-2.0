package com.example.taskmanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ListView
import android.widget.Toast
import com.example.taskmanager.R
import com.example.taskmanager.database.InputActivity
import com.example.taskmanager.database.TaskAdapter
import com.example.taskmanager.database.Taskk
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Taskk>
    lateinit var listview : ListView

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom) }
    private var clicked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ListView()
        setupListener()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Use home button to exit", Toast.LENGTH_SHORT).show()
    }

    private fun ListView(){
        ref = FirebaseDatabase.getInstance().getReference("TASK")
        list = mutableListOf()
        listview = findViewById(R.id.list_task)

        ref.addValueEventListener(object  : ValueEventListener{
            override fun onCancelled(db: DatabaseError) {
            }

            override fun onDataChange(db: DataSnapshot) {
                if (db!!.exists()){
                    for (h in db.children){
                        val task = h.getValue(Taskk::class.java)
                        list.add(task!!)
                    }
                    val adapter = TaskAdapter(this@MainActivity,R.layout.task,list)
                    listview.adapter = adapter
                }
            }
        })
    }

    private fun setupListener(){
        button_menu.setOnClickListener{
            floatingActionAnimation()
        }
        button_add.setOnClickListener {
            intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
        button_delete_all.setOnClickListener{
            Toast.makeText(this, "Delete all data", Toast.LENGTH_SHORT).show()
        }
        button_logout.setOnClickListener {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        }
    }

    private fun floatingActionAnimation() {
        setVisibility(clicked)
        startAnimation(clicked)
        clicked = !clicked
    }

    private fun startAnimation(clicked : Boolean) {
        if (clicked){
            button_logout.startAnimation(toBottom)
            button_delete_all.startAnimation(toBottom)
            button_add.startAnimation(toBottom)
            button_menu.startAnimation(rotateClose)
        }else{
            button_logout.startAnimation(fromBottom)
            button_delete_all.startAnimation(fromBottom)
            button_add.startAnimation(fromBottom)
            button_menu.startAnimation(rotateOpen)
        }
    }

    private fun setVisibility(clicked : Boolean) {
        if (clicked){
            button_logout.visibility = View.GONE
            button_delete_all.visibility = View.GONE
            button_add.visibility = View.GONE
        }else{
            button_logout.visibility = View.VISIBLE
            button_delete_all.visibility = View.VISIBLE
            button_add.visibility = View.VISIBLE
        }
    }
}
