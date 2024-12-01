package com.example.kelimekartlarim.FactoryKelimeSetleri

import com.example.kelimekartlarim.Database.Kelime

interface KelimeSeti {
    fun kelimeler() : ArrayList<Kelime>
}