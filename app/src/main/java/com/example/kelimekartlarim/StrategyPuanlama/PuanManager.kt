package com.example.kelimekartlarim.StrategyPuanlama

import android.content.Context
import android.content.SharedPreferences

object PuanManager { //singleton
    private const val PREF_NAME = "PuanPreferences"
    private const val KEY_PUAN = "puan_key"
    private var sharedPreferences: SharedPreferences? = null
    var puan: Int = 0
        private set

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        puan = sharedPreferences?.getInt(KEY_PUAN, 0) ?: 0
    }

    fun puanEkleme(eklenecekPuan: Int) {
        puan += eklenecekPuan
        puanSakla()
    }

    private fun puanSakla() {
        sharedPreferences?.edit()?.putInt(KEY_PUAN, puan)?.apply()
    }
}