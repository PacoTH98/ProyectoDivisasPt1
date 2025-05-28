package com.example.proyectodivisaspt1.red

import retrofit2.Response
import retrofit2.http.GET

interface ApiDivisas {
    @GET("v6/099b4e5ac1e58120c2cee8dc/latest/MXN")
    suspend fun obtenerTasas(): Response<RespuestaDivisas>
}
