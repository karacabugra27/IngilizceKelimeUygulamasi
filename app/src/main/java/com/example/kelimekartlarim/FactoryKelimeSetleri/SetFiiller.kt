package com.example.kelimekartlarim.FactoryKelimeSetleri

import com.example.kelimekartlarim.Database.DatabaseManager
import com.example.kelimekartlarim.Database.Kelime

class SetFiiller(private val dbManager: DatabaseManager) : KelimeSeti {
    override fun kelimeler(): ArrayList<Kelime> {
        return dbManager.kelimeSetiGetir("TableFiiller")
    }

    override fun rastgeleKelimeAl(): Kelime {
        return dbManager.rastgeleKelimeAl("TableFiiller")
    }

}