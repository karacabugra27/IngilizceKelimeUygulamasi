package com.example.kelimekartlarim.StrategyPuanlama

class StrategyManager(private var strategy: IStrategyPuanlama) {
    fun strategyBelirle(strategy : IStrategyPuanlama) {
        this.strategy = strategy
    }

    fun puanHesaplama(dogruSayisi: Int): Int {
        return strategy.puanHesapla(dogruSayisi)
    } //stratejiye yollayıp doğru sayısıyla puanını hesaplıyoruz.(zorluğa göre)
}