package com.example.kelimekartlarim.FactoryKelimeSetleri

import com.example.kelimekartlarim.Database.DatabaseManager

class SetFactory(private val dbManager: DatabaseManager) {
    fun createKelimeSeti(tableName : String) : KelimeSeti {
        return when(tableName) {
            "TableHayvanlar" -> SetHayvanlar(dbManager)
            "TableFiiller" -> SetFiiller(dbManager)
            "TableIsimler" -> SetIsimler(dbManager)
            "TableKiyafetler" -> SetKiyafetler(dbManager)
            else -> throw IllegalArgumentException("tablo bilinmiyor $tableName ")
        }
    }
}