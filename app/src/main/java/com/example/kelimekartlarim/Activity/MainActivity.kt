package com.example.kelimekartlarim.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kelimekartlarim.Database.DatabaseKopyalama
import com.example.kelimekartlarim.Database.DatabaseOpenHelper
import com.example.kelimekartlarim.R
import com.example.kelimekartlarim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val databaseName = "kelimekartlarim.db"
    lateinit var dbHelper: DatabaseOpenHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        dbHelper = DatabaseOpenHelper.getInstance(this)
        kopyala()

    }

    fun kopyala() {
        val dbKopya = DatabaseKopyalama(this)
        dbKopya.kopyala(databaseName, R.raw.kelimekartlarim)
    }

    fun btnQuiz(view: View) {
        val intent = Intent(this, ActivityQuizSecim::class.java)
        startActivity(intent)
    }

    fun btnKelime(view: View) {
        val intent = Intent(this, ActivityKelimeOgren::class.java)
        startActivity(intent)
    }
}