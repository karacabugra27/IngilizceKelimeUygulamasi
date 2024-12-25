package com.example.kelimekartlarim.Activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kelimekartlarim.Database.DatabaseManager
import com.example.kelimekartlarim.Database.DatabaseOpenHelper
import com.example.kelimekartlarim.Database.Kelime
import com.example.kelimekartlarim.FactoryKelimeSetleri.KelimeSeti
import com.example.kelimekartlarim.FactoryKelimeSetleri.SetFactory
import com.example.kelimekartlarim.R
import com.example.kelimekartlarim.StrategyPuanlama.PuanManager
import com.example.kelimekartlarim.StrategyPuanlama.StrategyManager
import com.example.kelimekartlarim.StrategyPuanlama.StrategySeviye1
import com.example.kelimekartlarim.StrategyPuanlama.StrategySeviye2
import com.example.kelimekartlarim.StrategyPuanlama.StrategySeviye3
import com.example.kelimekartlarim.databinding.ActivityQuizBinding
import com.example.kelimekartlarim.setupDialog
import java.text.DecimalFormat
import kotlin.math.roundToInt

class ActivityQuiz : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    //database işlemleri
    private lateinit var dbManager: DatabaseManager
    private lateinit var kelimeSeti: KelimeSeti
    private lateinit var dogruKelime: Kelime

    private val tumKelimeler = mutableListOf<Kelime>()
    private val secenekler = mutableListOf<Kelime>()
    private val sorulanSorular = mutableListOf<Kelime>()

    //doğru yanlış sayı kontolü
    private var dogruSayisi = 0
    private var yanlisSayisi = 0
    private var soruNo = 0

    //countdowntimer
    private val countdownTime = 20
    private val clockTime = (countdownTime * 1000).toLong()
    private val progressTime = (clockTime / 1000).toFloat()
    private lateinit var customCountdownTimer: CustomCountdownTimer

    private val scoreDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.score_dialog)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizBaslatma()


        //countdowntimer kod blok başlangıç

        var secondsLeft = 0
        customCountdownTimer = object : CustomCountdownTimer(clockTime, 1000) {}
        customCountdownTimer.onTick = { millisUntilFinished ->
            val second = (millisUntilFinished / 1000.0f).roundToInt()
            if (second != secondsLeft) {
                secondsLeft = second

                timerFormat(
                    secondsLeft
                )
            }
        }
        customCountdownTimer.onFinish = {
            showScoreDialog()
        }
        binding.circularProgressBar.max = progressTime.toInt()
        binding.circularProgressBar.progress = progressTime.toInt()

        customCountdownTimer.startTimer()

        //countdowntimer kod blok bitiş


        // score dialog kod blok başlangıç

        val anaMenuBtn: Button = scoreDialog.findViewById(R.id.anaMenuButton)
        val kategoriBtn: Button = scoreDialog.findViewById(R.id.kategoriButton)

        anaMenuBtn.setOnClickListener {
            val intent = Intent(this@ActivityQuiz, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        kategoriBtn.setOnClickListener {
            val intent = Intent(this@ActivityQuiz, ActivityQuizSecim::class.java)
            startActivity(intent)
            scoreDialog.dismiss()
        }
        //score dialog kod blok bitiş
    }

    override fun onPause() {
        super.onPause()
        if (scoreDialog.isShowing) {
            scoreDialog.dismiss()
        }
    }


    private fun quizBaslatma() {
        kelimeSetiniYukle()
        siradakiSoruyaGecis()
        butonSecenekSecmesi()
    }

    private fun zorlukSeviyeVePuanEkleme() {
        val tableName = intent.getStringExtra("tableName")
        val zorlukSeviyesi = when (tableName) {
            "Hayvanlar" -> StrategySeviye1()
            "Fiiller" -> StrategySeviye2()
            "Kıyafetler" -> StrategySeviye2()
            "İsimler" -> StrategySeviye3()
            "Bildiğim Kelimeler" -> StrategySeviye1()
            "Bilmediğim Kelimeler" -> StrategySeviye3()
            else -> StrategySeviye1()
        }
        val strategyManager = StrategyManager(zorlukSeviyesi)
        val puan = strategyManager.puanHesaplama(dogruSayisi)
        PuanManager.puanEkleme(puan)
    }

    private fun kelimeSetiniYukle() { //aktardığım tabloyu alma ve ilgili kelime setini getirme işlemi
        val tableName = intent.getStringExtra("tableName")
        binding.toolbar.toolbarTitle.text = tableName
        dbManager = DatabaseManager(DatabaseOpenHelper.getInstance(this))
        kelimeSeti = SetFactory(dbManager).createKelimeSeti(tableName!!)
        tumKelimeler.addAll(kelimeSeti.kelimeler())

    }

    private fun siradakiSoruyaGecis() {
        if (sorulanSorular.size < 10) {
            soruNo++
            dogruKelime = rastgeleSoruAl()
            seceneklerOlustur()
            updateUI()
        } else {
            customCountdownTimer.pauseTimer()
            zorlukSeviyeVePuanEkleme()//strategy pattern
            showScoreDialog()
        }
    }

    private fun rastgeleSoruAl(): Kelime {
        var rastgeleKelime: Kelime
        do {
            rastgeleKelime = tumKelimeler.random()
        } while (sorulanSorular.contains(rastgeleKelime) && sorulanSorular.size < 10)
        sorulanSorular.add(rastgeleKelime)
        return rastgeleKelime
    }

    private fun seceneklerOlustur() {
        secenekler.clear()
        secenekler.add(dogruKelime)
        val kullanilabilecekKelimeler = tumKelimeler.filter { it != dogruKelime }
        val rastgeleKelimeler = kullanilabilecekKelimeler.shuffled().take(3)
        secenekler.addAll(rastgeleKelimeler)
        secenekler.shuffle()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        binding.soruSayisiText.text = "Soru $soruNo/10"
        binding.soruText.text = dogruKelime.AdEng
        binding.firstOptionButton.text = secenekler[0].AdTurkce
        binding.secondOptionButton.text = secenekler[1].AdTurkce
        binding.thirdOptionButton.text = secenekler[2].AdTurkce
        binding.fourthOptionButton.text = secenekler[3].AdTurkce
    }

    private fun butonSecenekSecmesi() {
        binding.firstOptionButton.setOnClickListener {
            dogruYanlisKontrolu(binding.firstOptionButton.text.toString())
        }
        binding.secondOptionButton.setOnClickListener {
            dogruYanlisKontrolu(binding.secondOptionButton.text.toString())
        }
        binding.thirdOptionButton.setOnClickListener {
            dogruYanlisKontrolu(binding.thirdOptionButton.text.toString())
        }
        binding.fourthOptionButton.setOnClickListener {
            dogruYanlisKontrolu(binding.fourthOptionButton.text.toString())
        }
    }

    private fun dogruYanlisKontrolu(secilen: String) {
        if (secilen == dogruKelime.AdTurkce) {
            dogruSayisi++
        } else {
            yanlisSayisi++
        }
        siradakiSoruyaGecis()
    }

    @SuppressLint("SetTextI18n")
    private fun showScoreDialog() {
        val dogru = scoreDialog.findViewById<TextView>(R.id.skorDogru)
        val yanlis = scoreDialog.findViewById<TextView>(R.id.skorYanlis)

        dogru.text = this.dogruSayisi.toString()
        yanlis.text = this.yanlisSayisi.toString()

        scoreDialog.show()
    }

    private fun timerFormat(secondsLeft: Int) {

        binding.circularProgressBar.progress = secondsLeft
        val decimalFormat = DecimalFormat("00")

        val timeFormat1 = decimalFormat.format(secondsLeft)

        binding.timeText.text = timeFormat1
    }

}