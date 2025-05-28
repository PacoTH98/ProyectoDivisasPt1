package com.example.proyectodivisaspt1

import android.app.Application
import androidx.work.*
import com.example.proyectodivisaspt1.sincronizador.TrabajadorSincronizacion
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val solicitud = PeriodicWorkRequestBuilder<TrabajadorSincronizacion>(
            1, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "sincronizar_divisas",
            ExistingPeriodicWorkPolicy.KEEP,
            solicitud
        )
    }
}
