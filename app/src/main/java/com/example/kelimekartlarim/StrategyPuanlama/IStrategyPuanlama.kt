package com.example.kelimekartlarim.StrategyPuanlama

interface IStrategyPuanlama {
    fun puanHesapla(dogruSayisi : Int) : Int
}