package com.example.dikasisabarmazseh.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.dikasisabarmazseh.R

class TugasAdapter(val mtcx : Context, val layoutresId : Int, val list : List<Tugas>) : ArrayAdapter<Tugas>(mtcx, layoutresId, list){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // TODO : SELECTORS
        val layoutInflater : LayoutInflater = LayoutInflater.from(mtcx)
        val view : View = layoutInflater.inflate(layoutresId, null)
        val teksTampilTugas = view.findViewById<TextView>(R.id.teks_tampil_tugas)
        val teksTampilMapel = view.findViewById<TextView>(R.id.teks_tampil_mapel)
        val tugas = list[position]

        teksTampilTugas.text = tugas.tugas
        teksTampilMapel.text = tugas.mapel

        return view
    }
}