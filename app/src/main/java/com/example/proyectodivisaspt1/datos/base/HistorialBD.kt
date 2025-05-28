package com.example.proyectodivisaspt1.datos.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistorialConsulta::class], version = 1)
abstract class HistorialBD : RoomDatabase() {
    abstract fun historialDAO(): HistorialDAO

    companion object {
        @Volatile
        private var INSTANCIA: HistorialBD? = null

        fun obtenerInstancia(context: Context): HistorialBD {
            return INSTANCIA ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HistorialBD::class.java,
                    "historial_db"
                ).build().also { INSTANCIA = it }
            }
        }
    }
}
