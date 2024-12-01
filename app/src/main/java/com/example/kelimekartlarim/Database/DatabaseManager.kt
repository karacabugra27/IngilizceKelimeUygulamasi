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

    fun kelimeGetir(tableName: String, Id: Int): Kelime? {
        val db = dbOpenHelper.readableDatabase
        val sql = "select * from $tableName where Id = ?"
        val cursor = db.rawQuery(sql, arrayOf(Id.toString()))

        var kelime: Kelime? = null
        if (cursor.moveToFirst() != null) {
            kelime = Kelime()
            kelime!!.Id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"))
            kelime!!.AdTurkce = cursor.getString(cursor.getColumnIndexOrThrow("AdTurkce"))
            kelime!!.AdEng = cursor.getString(cursor.getColumnIndexOrThrow("AdEng"))
        }
        cursor.close()
        db.close()
        return kelime

    }
}