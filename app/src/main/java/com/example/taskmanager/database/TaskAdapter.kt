package com.example.taskmanager.database

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.taskmanager.R
import com.example.taskmanager.ui.MainActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.update.view.*

class TaskAdapter(val ctx : Context, val layoutresID : Int, val list : List<Taskk>) : ArrayAdapter<Taskk>(ctx, layoutresID, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // TODO : SELECTOR VAL
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctx)
        val view : View = layoutInflater.inflate(layoutresID, null)
        val TT = view.findViewById<TextView>(R.id.text_task)
        val TS = view.findViewById<TextView>(R.id.text_subject)
        val BTU = view.findViewById<ImageButton>(R.id.button_update)
        val BSU = view.findViewById<ImageButton>(R.id.button_delete)
        val task = list[position]

        TT.text = task.task
        TS.text = task.subject

        // TODO : LISTENER
        BTU.setOnClickListener{
            showUpdateDialog(task)
        }
        BSU.setOnClickListener {
            deleteData(task)
        }

        return view
    }

    // TODO : DELETE DATA
    private fun deleteData(taskk: Taskk) {
        val progessDialog = ProgressDialog(context, com.google.android.material.R.style.Theme_Material3_Dark_Dialog_Alert)
        progessDialog.isIndeterminate = true
        progessDialog.setMessage("Deleting...")
        progessDialog.show()
        val db = FirebaseDatabase.getInstance().getReference("TASK")
        db.child(taskk.id).removeValue().addOnCompleteListener {
            Toast.makeText(ctx, "Data deleted!", Toast.LENGTH_SHORT).show()
            val intent = Intent(ctx, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    // TODO : UPDATE DATA
    private fun showUpdateDialog(taskk: Taskk) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Edit")
        val inflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.update, null)
        val textTask = view.update_task
        val textSubject = view.update_subject

        textTask.setText(taskk.task)
        textSubject.setText(taskk.subject)

        builder.setView(view)

        builder.setPositiveButton("Update") { _, _ ->
            val db = FirebaseDatabase.getInstance().getReference("TASK")
            val tasks = textTask.text.toString().trim()
            val subject = textSubject.text.toString().trim()
            val taskk = Taskk(taskk.id, tasks, subject)
            if (tasks.isEmpty() || subject.isEmpty()) {
                Toast.makeText(ctx, "This can't be empty", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            } else {
                db.child(taskk.id).setValue(taskk).addOnCompleteListener {
                    Toast.makeText(ctx, "Data updated!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("Cancel"){ _, _ ->
        }
        val alert = builder.create()
        alert.show()
    }
}