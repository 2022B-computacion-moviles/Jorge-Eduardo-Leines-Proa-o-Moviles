package com.example.deber_examen_real.basedatos
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ConexionSQLiteHelper(context: Context?) : SQLiteOpenHelper(context, "AL3", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL.CREAR_TABLA_AUTOR)
        db?.execSQL(SQL.CREAR_TABLA_LIBRO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS "+ SQL.TABLA_AUTOR)
        db?.execSQL("DROP TABLE IF EXISTS "+ SQL.TABLA_LIBROS)
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys = ON;")
    }
}