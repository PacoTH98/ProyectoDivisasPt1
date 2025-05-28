package com.example.proyectodivisaspt1.repositorio

import android.content.Context
import android.util.Log
import com.example.proyectodivisaspt1.datos.base.DivisaBD
import com.example.proyectodivisaspt1.datos.base.DivisaEntidad
import com.example.proyectodivisaspt1.red.RetrofitCliente
import com.google.gson.Gson
import java.util.*

class RepositorioDivisas(private val contexto: Context) {

    suspend fun sincronizar() {
        Log.d("RepositorioDivisas", "🔄 Iniciando sincronización...")

        val respuesta = RetrofitCliente.api.obtenerTasas()

        if (respuesta.isSuccessful) {
            val datos = respuesta.body()
            Log.d("RepositorioDivisas", "🌐 Datos recibidos: $datos")

            if (datos != null && datos.result == "success") {
                val tasasJson = Gson().toJson(datos.rates)

                val entidad = DivisaEntidad(
                    fecha = datos.date,
                    base = datos.base,
                    tasasJson = tasasJson
                )

                DivisaBD.obtenerBD(contexto).obtenerDAO().insertar(entidad)
                Log.d("RepositorioDivisas", "✅ Datos guardados en BD")
            } else {
                Log.e("RepositorioDivisas", "❌ Resultado inesperado: ${datos?.result}")
                throw Exception("Resultado inesperado: ${datos?.result}")
            }
        } else {
            Log.e("RepositorioDivisas", "❌ Error HTTP: ${respuesta.code()}")
            throw Exception("Error HTTP ${respuesta.code()}")
        }
    }
}
