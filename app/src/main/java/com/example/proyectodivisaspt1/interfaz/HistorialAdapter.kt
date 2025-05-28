package com.example.proyectodivisaspt1.interfaz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectodivisaspt1.R
import com.example.proyectodivisaspt1.datos.base.HistorialConsulta

class HistorialAdapter(private var lista: List<HistorialConsulta>) :
    RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    fun actualizar(nuevaLista: List<HistorialConsulta>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(vista)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val item = lista[position]
        holder.texto.text = "📅 ${item.fecha} 🕒 ${item.hora}\n💱 1 ${item.base} = ${item.tasaBaseADestino} ${item.destino}\n💸 1 ${item.destino} = ${item.tasaDestinoABase} ${item.base}"
    }

    override fun getItemCount(): Int = lista.size

    class HistorialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val texto: TextView = view.findViewById(R.id.textoHistorial)
    }
}
