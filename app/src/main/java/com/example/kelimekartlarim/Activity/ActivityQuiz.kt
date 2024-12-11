package com.example.kelimekartlarim.Activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kelimekartlarim.CustomCountdownTimer
import com.example.kelimekartlarim.R
import com.example.kelimekartlarim.databinding.ActivityQuizBinding
import com.example.kelimekartlarim.setupDialog
import java.text.DecimalFormat
import kotlin.math.roundToInt

class ActivityQuiz : AppCompatActivity() {
    lateinit var binding: ActivityQuizBinding

    private val countdownTime = 20
    private val clockTime = (countdownTime * 1000).toLong()
    private val progressTime = (clockTime / 1000).toFloat()
    private lateinit var customCountdownTimer: CustomCountdownTimer

    private val scoreDialog: Dialog by lazy {
        Dialog(this,R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.score_dialog)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)



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


        val anaMenuBtn : Button = scoreDialog.findViewById(R.id.anaMenuButton)
        val kategoriBtn : Button = scoreDialog.findViewById(R.id.kategoriButton)

        anaMenuBtn.setOnClickListener {
            val intent = Intent(this@ActivityQuiz,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        kategoriBtn.setOnClickListener {
            val intent = Intent(this@ActivityQuiz,ActivityQuizSecim::class.java)
            startActivity(intent)
            scoreDialog.dismiss()
        }
    }

    private fun showScoreDialog() {
        val dogruSayisi = scoreDialog.findViewById<TextView>(R.id.skorDogru)
        val yanlisSayisi = scoreDialog.findViewById<TextView>(R.id.skorYanlis)

        dogruSayisi.text = ""
        yanlisSayisi    .text = ""

        scoreDialog.show()
    }

    private fun timerFormat(secondsLeft: Int) {

        binding.circularProgressBar.progress = secondsLeft
        val decimalFormat = DecimalFormat("00")

        val timeFormat1 = decimalFormat.format(secondsLeft)

        binding.timeText.text = timeFormat1
    }


}