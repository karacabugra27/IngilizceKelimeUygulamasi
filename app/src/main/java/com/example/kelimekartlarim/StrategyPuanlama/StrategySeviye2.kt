package com.example.kelimekartlarim.StrategyPuanlama

class StrategySeviye2 : IStrategyPuanlama{
    override fun puanHesapla(dogruSayisi: Int): Int {
        return dogruSayisi * 3
    }

}