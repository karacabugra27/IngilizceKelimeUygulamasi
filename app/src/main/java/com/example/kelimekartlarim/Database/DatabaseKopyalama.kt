package com.example.kelimekartlarim.Database

import android.content.Context
import java.io.FileOutputStream
import java.io.InputStream

class DatabaseKopyalama(private val context : Context) {
    fun kopyala(databaseName : String, rawResourceId : Int){
        val dbPath = context.getDatabasePath(databaseName)

        if (dbPath.exists()){
            return
        }

        val inputS : InputStream = context.resources.openRawResource(rawResourceId)

        FileOutputStream(dbPath.absolutePath).use {
            var buffer = ByteArray(1024)
            var uzunluk = inputS.read(buffer)
            do {
                it.write(buffer,0,uzunluk)
                uzunluk = inputS.read(buffer)
            } while (uzunluk >0)

            inputS.close()
        }
    }
}