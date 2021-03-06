package com.example.taskmanager.database

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.InputDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.ui.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.taskrv.view.*

class RVTaskAdapter(private val Rvlist : ArrayList<Taskk>, val ctx : Context) : RecyclerView.Adapter<RVTaskAdapter.RVTaskViewholder>() {

    // TODO : AUTHENTICATION
    private lateinit var auth : FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVTaskViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.taskrv, parent, false)
        return RVTaskViewholder(view)
    }

    // TODO : SELECTOR
    override fun onBindViewHolder(holder: RVTaskViewholder, position: Int) {
        val currentItem = Rvlist[position]
        holder.tietRvTask.setText(currentItem.task)
        holder.tietRvTask.inputType = InputDevice.KEYBOARD_TYPE_NONE
        holder.tietRvSub.setText(currentItem.subject)
        holder.tietRvSub.inputType = InputDevice.KEYBOARD_TYPE_NONE
        holder.itemView.imageButtonEdit.setOnClickListener{
            showUpdateDialog(currentItem)
        }
        holder.itemView.imageButtonDelete.setOnClickListener {
            deleteData(currentItem)
        }
        holder.tietRvTask.setOnClickListener {
            Toast.makeText(ctx, "Task : " + currentItem.task, Toast.LENGTH_LONG).show()
        }
        holder.tietRvSub.setOnClickListener {
            Toast.makeText(ctx, "Subject : " + currentItem.subject, Toast.LENGTH_LONG).show()
        }
    }

    // TODO : UPDATE DATA
    private fun showUpdateDialog(taskk: Taskk) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Edit")
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.update, null)
        val updateTask = view.findViewById<TextInputEditText>(R.id.update_task)
        val updateSub = view.findViewById<TextInputEditText>(R.id.update_subject)
        auth = Firebase.auth
        val user = auth.currentUser

        updateTask.setText(taskk.task)
        updateSub.setText(taskk.subject)

        builder.setView(view)

        builder.setPositiveButton("Update"){_, _ ->
            val db = FirebaseDatabase.getInstance().getReference(user?.uid.toString())
            val tasks = updateTask.text.toString().trim()
            val subject = updateSub.text.toString().trim()
            val taskk = Taskk(taskk.id, tasks, subject)
            if (tasks.isEmpty() || subject.isEmpty()) {
                Toast.makeText(ctx, "This can't be empty", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            } else {
                db.child(taskk.id.toString()).setValue(taskk).addOnCompleteListener {
                    Toast.makeText(ctx, "Data updated!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("Cancel"){ _, _ ->
        }
        val alert = builder.create()
        alert.show()
    }

    // TODO : DELETE
    private fun deleteData(taskk: Taskk) {
        val progressDialog = ProgressDialog(ctx, com.google.android.material.R.style.Theme_Material3_Dark_Dialog_Alert)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        auth = Firebase.auth
        val user = auth.currentUser
        progressDialog.show()
        val db = FirebaseDatabase.getInstance().getReference(user?.uid.toString())
        db.child(taskk.id.toString()).removeValue().addOnCompleteListener {
            Toast.makeText(ctx, "Data deleted!", Toast.LENGTH_SHORT).show()
            val intent = Intent(ctx, MainActivity::class.java)
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return Rvlist.size
    }

    // TODO : SELECTOR
    class RVTaskViewholder(view : View) : RecyclerView.ViewHolder(view){
        val tietRvTask : TextInputEditText = view.findViewById(R.id.tietrvtask)
        val tietRvSub : TextInputEditText = view.findViewById(R.id.tietrvsub)
    }
}