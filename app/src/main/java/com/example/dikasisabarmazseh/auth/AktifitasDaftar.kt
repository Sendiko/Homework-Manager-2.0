package com.example.dikasisabarmazseh.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dikasisabarmazseh.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_aktifitas_daftar.*

class AktifitasDaftar : AppCompatActivity() {

    // TODO : AUTHENTICATION
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktifitas_daftar)

        // TODO : AUTHENTICATION
        auth = Firebase.auth

        setupListener()
    }

    // TODO : SETUP LISTENER
    private fun setupListener() {
        tombolDaftar.setOnClickListener {
            DaftarAkun()
        }
        teksMasuk.setOnClickListener {
            intent = Intent(this, AktifitasMasuk::class.java)
            startActivity(intent)
        }
    }

    // TODO : DAFTAR AKUN KE FIREBASE
    private fun DaftarAkun() {

        // TODO : VAL SELECTOR
        val emailDaf = emailDaftar.text.toString()
        val passwordDaf = katasandiDaftar.text.toString()
        val confPassDaf = konfirmasisandi.text.toString()

        // TODO : INPUT CHECKER
        if (emailDaf.isBlank() || passwordDaf.isBlank()){
            Toast.makeText(this, "Email dan kata sandi harus diisi", Toast.LENGTH_SHORT).show()
            return
        }
        if (passwordDaf != confPassDaf){
            Toast.makeText(this, "Kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO : AUTHENTICATION
        auth.createUserWithEmailAndPassword(emailDaf, passwordDaf).addOnCompleteListener(this) {
            if (it.isSuccessful){
                Toast.makeText(this, "Berhasil daftar!", Toast.LENGTH_SHORT).show()
                intent = Intent(this, AktifitasMasuk::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Daftar Gagal", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}