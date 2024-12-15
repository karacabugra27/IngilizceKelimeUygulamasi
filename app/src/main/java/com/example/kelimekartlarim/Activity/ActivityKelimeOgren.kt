package com.example.kelimekartlarim.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.kelimekartlarim.CardStackAdapter.CardAdapter
import com.example.kelimekartlarim.Database.DatabaseManager
import com.example.kelimekartlarim.Database.DatabaseOpenHelper
import com.example.kelimekartlarim.Database.Kelime
import com.example.kelimekartlarim.databinding.ActivityKelimeOgrenBinding
import com.example.kelimekartlarim.databinding.KartLayoutBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod

class ActivityKelimeOgren : AppCompatActivity(), CardStackListener {
    lateinit var bindingKelimeOgren: ActivityKelimeOgrenBinding
    lateinit var bindingKart: KartLayoutBinding
    private val cardStackView by lazy { bindingKelimeOgren.cardStackView }
    private val layoutManager by lazy { CardStackLayoutManager(this, this) }
    private val cardStackAdapter by lazy { CardAdapter(kelimeleriGetir()) }
    private var kelimeler: List<Kelime> = kelimeleriGetir()
    private val rightSwipedWords = mutableListOf<Kelime>()
    private val leftSwipedWords = mutableListOf<Kelime>()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingKelimeOgren = ActivityKelimeOgrenBinding.inflate(layoutInflater)
        bindingKart = KartLayoutBinding.inflate(layoutInflater)
        setContentView(bindingKelimeOgren.root)

        initialize()


    }

    private fun kelimeleriGetir(): List<Kelime> {
        val db = DatabaseManager(DatabaseOpenHelper.getInstance(this))
        return db.tumTablolardanKelimeGetir()
    }

    private fun initialize() {
        cardStackView.layoutManager = layoutManager
        cardStackView.adapter = cardStackAdapter
        layoutManager.setStackFrom(StackFrom.Top)
        layoutManager.setVisibleCount(3)
        layoutManager.setTranslationInterval(8.0f)
        layoutManager.setScaleInterval(0.95f)
        layoutManager.setSwipeThreshold(0.3f)
        layoutManager.setMaxDegree(20.0f)
        layoutManager.setDirections(Direction.HORIZONTAL)
        layoutManager.setCanScrollHorizontal(true)
        layoutManager.setCanScrollVertical(false)
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        layoutManager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = layoutManager
        cardStackView.adapter = cardStackAdapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction?.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction?) {
        val currentKelime = kelimeler[currentIndex]
        if (direction == Direction.Right) {
            rightSwipedWords.add(currentKelime)
        } else if (direction == Direction.Left) {
            leftSwipedWords.add(currentKelime)
        }
        currentIndex++
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${layoutManager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${layoutManager.topPosition}")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        val appearedKelime = kelimeler[position]

        bindingKart.englishText.text = appearedKelime.AdEng
        bindingKart.turkishText.text = appearedKelime.AdTurkce

    }

    override fun onCardDisappeared(view: View?, position: Int) {
        val appearedKelime = kelimeler[position]

        bindingKart.englishText.text = appearedKelime.AdEng
        bindingKart.turkishText.text = appearedKelime.AdTurkce

    }
}
