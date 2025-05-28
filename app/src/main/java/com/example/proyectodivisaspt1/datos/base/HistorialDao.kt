package com.example.proyectodivisaspt1.datos.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistorialDAO {
    @Insert
    suspend fun insertar(historial: HistorialConsulta)

    @Query("DELETE FROM historial")
    suspend fun borrarTodo()

    @Query("SELECT * FROM historial ORDER BY id DESC")
    suspend fun obtenerTodo(): List<HistorialConsulta>
}
