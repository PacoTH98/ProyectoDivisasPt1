package com.example.proyectodivisaspt1.datos.base

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial")
data class HistorialConsulta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: String,
    val hora: String,
    val base: String,
    val destino: String,
    val tasaBaseADestino: Double,
    val tasaDestinoABase: Double
)
