package com.example.kelimekartlarim.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kelimekartlarim.CustomCountdownTimer
import com.example.kelimekartlarim.R
import com.example.kelimekartlarim.databinding.ActivityQuizBinding
import java.text.DecimalFormat
import kotlin.math.roundToInt

class ActivityQuiz : AppCompatActivity() {
    lateinit var binding: ActivityQuizBinding

    private val countdownTime = 20
    private val clockTime = (countdownTime * 1000).toLong()
    private val progressTime = (clockTime / 1000).toFloat()
    private lateinit var customCountdownTimer: CustomCountdownTimer

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
        }
        binding.circularProgressBar.max = progressTime.toInt()
        binding.circularProgressBar.progress = progressTime.toInt()

        customCountdownTimer.startTimer()
    }

    private fun timerFormat(secondsLeft: Int) {

        binding.circularProgressBar.progress = secondsLeft
        val decimalFormat = DecimalFormat("00")

        val timeFormat1 = decimalFormat.format(secondsLeft)

        binding.timeText.text = timeFormat1
    }
}