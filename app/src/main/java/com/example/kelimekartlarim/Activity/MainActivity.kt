package com.example.kelimekartlarim.Activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.kelimekartlarim.Database.DatabaseKopyalama
import com.example.kelimekartlarim.Database.DatabaseOpenHelper
import com.example.kelimekartlarim.R
import com.example.kelimekartlarim.StrategyPuanlama.PuanManager
import com.example.kelimekartlarim.databinding.ActivityMainBinding
import com.example.kelimekartlarim.setupDialog
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val databaseName = "kelimekartlarim.db"
    lateinit var dbHelper: DatabaseOpenHelper

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }
    }

    private val exitDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.exit_dialog)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback) // özelleştirilmiş geri tuşu fonksiyonu.

        PuanManager.init(this)

        dbHelper = DatabaseOpenHelper.getInstance(this)
        kopyala()

        val yesBtn: Button = exitDialog.findViewById(R.id.evetButton)
        val noBtn: Button = exitDialog.findViewById(R.id.hayirButton)

        yesBtn.setOnClickListener {
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }

        noBtn.setOnClickListener {
            exitDialog.dismiss()
        }

        binding.skor.text = PuanManager.puan.toString()

    }

    private fun kopyala() {
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

    private fun onBackPressedMethod() {
        exitDialog.show()
    }
}