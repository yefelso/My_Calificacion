package com.appyeff1.calificaciones

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "calificaciones.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_ALUMNOS = "alumnos"
        const val COLUMN_ID = "_id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_NOTA = "nota"
        const val COLUMN_GRADO = "grado"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_ALUMNOS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_APELLIDO TEXT, " +
                "$COLUMN_NOTA REAL, " +
                "$COLUMN_GRADO TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALUMNOS")
        onCreate(db)
    }

    // Método para agregar un alumno
    fun addAlumno(nombre: String, apellido: String, nota: Double, grado: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_APELLIDO, apellido)
            put(COLUMN_NOTA, nota)
            put(COLUMN_GRADO, grado)
        }
        db.insert(TABLE_ALUMNOS, null, values)
        db.close()
    }

    // Método para obtener todos los alumnos
    fun getAllAlumnos(): List<Alumno> {
        val alumnos = mutableListOf<Alumno>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_ALUMNOS", null)

        if (cursor.moveToFirst()) {
            do {
                val alumno = Alumno(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDO)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_NOTA)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GRADO))
                )
                alumnos.add(alumno)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return alumnos
    }

    // Método para actualizar un alumno
    fun updateAlumno(id: Int, nombre: String, apellido: String, nota: Double, grado: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_APELLIDO, apellido)
            put(COLUMN_NOTA, nota)
            put(COLUMN_GRADO, grado)
        }
        db.update(TABLE_ALUMNOS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteAlumno(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_ALUMNOS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}

