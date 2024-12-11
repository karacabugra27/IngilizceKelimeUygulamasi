package com.example.kelimekartlarim.FactoryKelimeSetleri

import com.example.kelimekartlarim.Database.DatabaseManager

class SetFactory(private val dbManager: DatabaseManager) {
    fun createKelimeSeti(tableName : String) : KelimeSeti {
        return when(tableName) {
            "Hayvanlar" -> SetHayvanlar(dbManager)
            "Fiiller" -> SetFiiller(dbManager)
            "İsimler" -> SetIsimler(dbManager)
            "Kıyafetler" -> SetKiyafetler(dbManager)
            else -> throw IllegalArgumentException("tablo bilinmiyor $tableName ")
        }
    }
}