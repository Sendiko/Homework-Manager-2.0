package com.example.taskmanager.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.taskmanager.R

class TaskAdapter(val cTx : Context, val layoutresID : Int, val list : List<Taskk>) : ArrayAdapter<Taskk>(cTx, layoutresID, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(cTx)
        val view : View = layoutInflater.inflate(layoutresID, null)
        val TT = view.findViewById<TextView>(R.id.text_task)
        val TS = view.findViewById<TextView>(R.id.text_subject)
        val task = list[position]

        TT.text = task.task
        TS.text = task.subject

        return view
    }
}