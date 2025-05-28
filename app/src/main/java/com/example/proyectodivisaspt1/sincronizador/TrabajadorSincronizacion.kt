package com.example.proyectodivisaspt1.sincronizador

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.proyectodivisaspt1.repositorio.RepositorioDivisas

class TrabajadorSincronizacion(
    private val contexto: Context,
    params: WorkerParameters
) : CoroutineWorker(contexto, params) {

    override suspend fun doWork(): Result {
        Log.d("TrabajadorSincronizacion", "🔄 Iniciando sincronización...")

        return try {
            RepositorioDivisas(contexto).sincronizar()
            Log.d("TrabajadorSincronizacion", "✅ Sincronización completada")
            Result.success()
        } catch (e: Exception) {
            Log.e("TrabajadorSincronizacion", "❌ Error al sincronizar", e)
            Result.retry()
        }
    }
}
