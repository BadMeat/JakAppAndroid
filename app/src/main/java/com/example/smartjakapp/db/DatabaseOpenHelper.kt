package com.example.smartjakapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by Bencoleng on 11/06/2019.
 */
class DatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 2) {

    companion object {
        private var instance: DatabaseOpenHelper? = null

        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx)
            }
            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.ID_ITEM to INTEGER + UNIQUE,
            Favorite.NAME to TEXT,
            Favorite.PHONE to TEXT,
            Favorite.ADDRESS to TEXT,
            Favorite.LAT to REAL,
            Favorite.LNG to REAL,
            Favorite.TYPE to INTEGER
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion > newVersion) {
            db?.dropTable(Favorite.TABLE_FAVORITE)
        }
    }
}


val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)