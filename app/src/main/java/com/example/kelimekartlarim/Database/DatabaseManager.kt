package com.example.kelimekartlarim.Database

import android.content.ContentValues

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
        val sql = "SELECT * FROM $tableName order by random()"
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

    fun kelimeEkleBildiklerim(AdEng: String, AdTurkce: String) {
        val db = dbOpenHelper.writableDatabase
        val cv = ContentValues().apply {
            put("AdTurkce", AdTurkce)
            put("AdEng", AdEng)
        }
        db.insert("TableBildiklerim", null, cv)
        db.close()
    }

    fun kelimeEkleBilmediklerim(AdEng: String, AdTurkce: String) {
        val db = dbOpenHelper.writableDatabase
        val cv = ContentValues().apply {
            put("AdTurkce", AdTurkce)
            put("AdEng", AdEng)
        }
        db.insert("TableBilmediklerim", null, cv)
        db.close()
    }

    fun kelimeSil(kelimeId : Int){
        val db = dbOpenHelper.writableDatabase
        val where = "id = ?"
        val whereArgs = arrayOf(kelimeId.toString())
        db.delete("TableGenel",where,whereArgs)
        db.close()
    }
}