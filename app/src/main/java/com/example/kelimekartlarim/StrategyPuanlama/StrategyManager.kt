package com.example.kelimekartlarim.StrategyPuanlama

class StrategyManager(private var strategy: IStrategyPuanlama) {
    fun puanHesaplama(dogruSayisi: Int): Int {
        return strategy.puanHesapla(dogruSayisi)
    } //stratejiye yollayıp doğru sayısıyla puanını hesaplıyoruz.(zorluğa göre)
}