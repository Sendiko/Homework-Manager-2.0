package com.example.dikasisabarmazseh.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dikasisabarmazseh.R
import com.example.dikasisabarmazseh.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_aktifitas_masuk.*

class AktifitasMasuk : AppCompatActivity() {

    // TODO : AUTHENTICATION
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktifitas_masuk)

        // TODO : AUTHENTICATION
        auth = FirebaseAuth.getInstance()
        setupListener()
    }

    // TODO : SETUPLISTENER
    private fun setupListener(){
        tombolMasuk.setOnClickListener {
            Masuk()
        }
        teksDaftar.setOnClickListener {
            intent = Intent(this, AktifitasDaftar::class.java)
            startActivity(intent)
        }
    }

    private fun Masuk() {
        val emailMas = emailMasuk.text.toString()
        val passwordMas = katasandiMasuk.text.toString()

        if (emailMas.isBlank() || passwordMas.isBlank()){
            Toast.makeText(this, "Email dan Kata sandi harus diisi", Toast.LENGTH_SHORT).show()
        }else{
            auth.signInWithEmailAndPassword(emailMas, passwordMas).addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Berhasil masuk!", Toast.LENGTH_SHORT).show()
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Gagal masuk", Toast.LENGTH_SHORT).show()
                    emailMasuk.setText("")
                    katasandiMasuk.setText("")
                }
            }
        }
    }
}