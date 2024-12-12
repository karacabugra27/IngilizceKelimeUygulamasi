package com.example.kelimekartlarim.StrategyPuanlama

class StrategySeviye1 : IStrategyPuanlama{
    override fun puanHesapla(dogruSayisi: Int): Int {
        return dogruSayisi * 1
    }

}