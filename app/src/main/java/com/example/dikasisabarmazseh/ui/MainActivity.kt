package com.example.dikasisabarmazseh.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.dikasisabarmazseh.R
import com.example.dikasisabarmazseh.data.Tugas
import com.example.dikasisabarmazseh.data.TugasAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // TODO : LISTVIEW
    lateinit var ref : DatabaseReference
    lateinit var list: MutableList<Tugas>
    lateinit var listVIew : ListView

    // TODO : SET ANIMATION
    private val puterBuka : Animation by lazy{ AnimationUtils.loadAnimation(this, R.anim.puter_buka)}
    private val puterTutup : Animation by lazy{ AnimationUtils.loadAnimation(this, R.anim.puter_tutup)}
    private val keBawah : Animation by lazy{ AnimationUtils.loadAnimation(this, R.anim.dari_bawah)}
    private val dariBawah : Animation by lazy{ AnimationUtils.loadAnimation(this, R.anim.ke_bawah)}
    private var pencet = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()

        // TODO : LISTVIEW
        ref = FirebaseDatabase.getInstance().getReference("TUGAS")
        list = mutableListOf()
        listVIew = list_tugas

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    for(h in p0.children){
                        val tugas = h.getValue(Tugas::class.java)
                        list.add(tugas!!)
                    }
                    val adapter = TugasAdapter(applicationContext, R.layout.tugas, list)
                    listVIew.adapter = adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    // TODO : SETUPLISTENER
    private fun setupListener(){
        tombol_pilihan.setOnClickListener {
            AnimasiMulai()
        }
        tombol_tambah.setOnClickListener {
            intent = Intent(this, AktifitasTambah::class.java)
            startActivity(intent)
        }
    }

    // TODO : FUNGSI ANIMASI
    private fun AnimasiMulai() {
        setAnimasi(pencet)
        setTerlihat(pencet)
        pencet = !pencet
    }

    // TODO : ANIMASI MULAI
    private fun setAnimasi(pencet : Boolean) {
        if (pencet){
            tombol_pilihan.startAnimation(puterBuka)
            tombol_tambah.startAnimation(keBawah)
        }else{
            tombol_pilihan.startAnimation(puterTutup)
            tombol_tambah.startAnimation(dariBawah)
        }
    }

    // TODO : KETERLIHATAN
    private fun setTerlihat(pencet : Boolean) {
        if (pencet){
            tombol_tambah.visibility = View.VISIBLE
        }else{
            tombol_tambah.visibility = View.GONE
        }
    }
}