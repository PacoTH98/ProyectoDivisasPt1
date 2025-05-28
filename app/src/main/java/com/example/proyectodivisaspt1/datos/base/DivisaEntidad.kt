package com.example.proyectodivisaspt1.datos.base

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "divisas")
data class DivisaEntidad(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: String,
    val base: String,
    val tasasJson: String
)