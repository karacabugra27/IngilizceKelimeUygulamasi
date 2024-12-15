package com.example.kelimekartlarim.Database

class DatabaseManager(private val dbOpenHelper: DatabaseOpenHelper) {

    fun kelimeSetiGetir(tableName: String): ArrayList<Kelime> {
        val kelimeListesi = ArrayList<Kelime>()

        val db = dbOpenHelper.readableDatabase
        val sql = "select * from $tableName "
        val cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst()) {
            var kelime: Kelime

            do {
                kelime = Kelime()
                kelime.Id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"))
                kelime.AdTurkce = cursor.getString(cursor.getColumnIndexOrThrow("AdTurkce"))
                kelime.AdEng = cursor.getString(cursor.getColumnIndexOrThrow("AdEng"))

                kelimeListesi.add(kelime)

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return kelimeListesi
    }

    fun kelimeGetir(tableName: String): List<Kelime> {
        val db = dbOpenHelper.readableDatabase
        val sql = "SELECT * FROM $tableName"
        val cursor = db.rawQuery(sql, null)

        val kelimeler = mutableListOf<Kelime>()

        while (cursor.moveToNext()) {
            val kelime = Kelime()
            kelime.Id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"))
            kelime.AdTurkce = cursor.getString(cursor.getColumnIndexOrThrow("AdTurkce"))
            kelime.AdEng = cursor.getString(cursor.getColumnIndexOrThrow("AdEng"))
            kelimeler.add(kelime)
        }
        cursor.close()
        db.close()
        return kelimeler
    }

    fun tumTablolardanKelimeGetir(): List<Kelime> {
        val db = dbOpenHelper.readableDatabase
        val tablolardanKelime = mutableListOf<Kelime>()

        val sql = "SELECT name FROM sqlite_master WHERE type='table'"
        val cursor = db.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val tableName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            if (tableName == "sqlite_sequence" || tableName == "android_metadata") {
                continue
            }
            val kelimeler = kelimeGetir(tableName)
            tablolardanKelime.addAll(kelimeler)
        }
        cursor.close()
        db.close()
        return tablolardanKelime
    }


    fun rastgeleKelimeAl(tableName: String): Kelime {
        val db = dbOpenHelper.readableDatabase
        val sql = "select * from $tableName order by random() limit 1"
        val cursor = db.rawQuery(sql, null)

        var kelime: Kelime? = null
        if (cursor.moveToFirst()) {
            kelime = Kelime()
            kelime.Id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"))
            kelime.AdTurkce = cursor.getString(cursor.getColumnIndexOrThrow("AdTurkce"))
            kelime.AdEng = cursor.getString(cursor.getColumnIndexOrThrow("AdEng"))
        }
        cursor.close()
        db.close()
        return kelime ?: throw IllegalArgumentException("Kelime bulunamadı.")

    }
}