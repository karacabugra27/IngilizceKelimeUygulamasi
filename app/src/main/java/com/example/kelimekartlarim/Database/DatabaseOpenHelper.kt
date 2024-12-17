package com.example.kelimekartlarim.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseOpenHelper private constructor(context: Context?) : SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        private const val name = "kelimekartlarim.db"
        private const val version = 3


        @Volatile
        private var instance: DatabaseOpenHelper? = null

        fun getInstance(context: Context): DatabaseOpenHelper {
            return instance ?: synchronized(this) {
                instance ?: DatabaseOpenHelper(context.applicationContext).also { instance = it }
            }

        }
    }
}
