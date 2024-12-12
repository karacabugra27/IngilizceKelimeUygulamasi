package com.example.kelimekartlarim.StrategyPuanlama

class StrategySeviye3 : IStrategyPuanlama{
    override fun puanHesapla(dogruSayisi: Int): Int {
        return dogruSayisi * 5
    }

}