package com.example.proyectodivisaspt1.datos.base

import androidx.room.*

@Dao
interface DivisaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(divisa: DivisaEntidad)

    @Query("SELECT * FROM divisas ORDER BY fecha DESC")
    fun obtenerTodas(): List<DivisaEntidad>
}
