package com.example.proyectodivisaspt1.interfaz

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectodivisaspt1.R
import com.example.proyectodivisaspt1.datos.base.HistorialBD
import com.example.proyectodivisaspt1.datos.base.HistorialConsulta
import com.example.proyectodivisaspt1.red.RetrofitCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var resultado: TextView
    private lateinit var btnConsultar: Button
    private lateinit var btnBorrarHistorial: Button
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: HistorialAdapter

    private val historialDAO by lazy {
        HistorialBD.obtenerInstancia(applicationContext).historialDAO()
    }


    private val listaHistorial = mutableListOf<HistorialConsulta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinnerMonedas)
        resultado = findViewById(R.id.resultadoCambio)
        btnConsultar = findViewById(R.id.btnConsultar)
        btnBorrarHistorial = findViewById(R.id.btnBorrarHistorial)
        recycler = findViewById(R.id.recyclerHistorial)

        adapter = HistorialAdapter(listaHistorial)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val monedas = listOf("USD", "EUR", "JPY", "CAD", "GBP", "BRL", "CLP")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, monedas)
        spinner.adapter = adapterSpinner
        spinner.setSelection(monedas.indexOf("USD"))

        btnConsultar.setOnClickListener {
            val monedaSeleccionada = spinner.selectedItem.toString()
            consultarTasa(monedaSeleccionada)
        }

        btnBorrarHistorial.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                historialDAO.borrarTodo()
                launch(Dispatchers.Main) {
                    listaHistorial.clear()
                    adapter.actualizar(listaHistorial)
                    resultado.text = "💸 Resultado"
                }
            }
        }

        cargarHistorialDesdeBD()
    }

    private fun consultarTasa(moneda: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val respuesta = RetrofitCliente.api.obtenerTasas()
                if (respuesta.isSuccessful) {
                    val body = respuesta.body()
                    val tasa = body?.rates?.get(moneda)
                    val inversa = if (tasa != null && tasa != 0.0) 1 / tasa else 0.0
                    val fechaActual = body?.date ?: obtenerFechaActual()
                    val horaActual = obtenerHoraActual()

                    if (tasa != null) {
                        val item = HistorialConsulta(
                            fecha = fechaActual,
                            hora = horaActual,
                            base = "MXN",
                            destino = moneda,
                            tasaBaseADestino = tasa,
                            tasaDestinoABase = inversa
                        )
                        historialDAO.insertar(item)

                        launch(Dispatchers.Main) {
                            resultado.text = "📅 $fechaActual 🕒 $horaActual\n💱 1 MXN = $tasa $moneda\n💸 1 $moneda = $inversa MXN"
                            cargarHistorialDesdeBD()
                        }
                    }
                } else {
                    launch(Dispatchers.Main) {
                        resultado.text = "❌ Error al obtener tasa"
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    resultado.text = "❌ Error: ${e.message}"
                }
            }
        }
    }

    private fun cargarHistorialDesdeBD() {
        lifecycleScope.launch(Dispatchers.IO) {
            val historial = historialDAO.obtenerTodo()
            launch(Dispatchers.Main) {
                listaHistorial.clear()
                listaHistorial.addAll(historial)
                adapter.actualizar(listaHistorial)
            }
        }
    }

    private fun obtenerFechaActual(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    private fun obtenerHoraActual(): String {
        return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
    }
}
