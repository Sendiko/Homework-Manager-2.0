package com.example.taskmanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ListView()
        setupListener()
    }
    private fun ListView(){
        ref = FirebaseDatabase.getInstance().getReference("TASK")
        list = mutableListOf()
        listview = findViewById(R.id.list_task)

        ref.addValueEventListener(object  : ValueEventListener{
            override fun onCancelled(db: DatabaseError) {
                TODO("Not yet implemented")
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
        button_menu.setOnClickListener {
            intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
    }
}
