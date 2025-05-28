package com.example.proyectodivisaspt1.datos.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DivisaEntidad::class, HistorialConsulta::class],
    version = 1
)
abstract class DivisaBD : RoomDatabase() {
    abstract fun obtenerDAO(): DivisaDAO
    abstract fun daoHistorial(): HistorialDAO

    companion object {
        @Volatile
        private var instancia: DivisaBD? = null

        fun obtenerBD(context: Context): DivisaBD {
            return instancia ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DivisaBD::class.java,
                    "bd_divisas"
                ).build().also { instancia = it }
            }
        }
    }
}
