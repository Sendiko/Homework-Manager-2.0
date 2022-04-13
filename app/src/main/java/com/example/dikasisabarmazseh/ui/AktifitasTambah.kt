package com.example.dikasisabarmazseh.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dikasisabarmazseh.R
import com.example.dikasisabarmazseh.data.Tugas
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_aktifitas_tambah.*

class AktifitasTambah : AppCompatActivity() {

    // TODO : DATABASE REFRERENCE
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktifitas_tambah)

        // TODO : DATABASE REFRERENCE
        ref = FirebaseDatabase.getInstance().getReference("TUGAS")
        setupListener()
    }

    // TODO : SETUPLISTENER
    private fun setupListener(){
        tombolSimpan.setOnClickListener {
            simpanData()
//            Toast.makeText(this, "cek", Toast.LENGTH_SHORT).show()
        }
    }

    // TODO : FUNCTION SIMPAN
    private fun simpanData() {
        // TODO : SELECTOR
        val namaTugas = nama_tugas.text.toString()
        val namaMapel = mata_pelajaran.text.toString()

        // TODO : VAL SIMPAN
        val tugas = Tugas(namaTugas, namaMapel)
        val tugasId = ref.push().key.toString()

        // TODO : SYNTAX SIMPAN
        ref.child(tugasId).setValue(tugas).addOnCompleteListener {
            Toast.makeText(this, "Tugas tercatat!", Toast.LENGTH_SHORT).show()
            nama_tugas.setText("")
            mata_pelajaran.setText("")
        }
    }
}