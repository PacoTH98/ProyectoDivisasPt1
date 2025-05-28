package com.example.proyectodivisaspt1.interfaz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectodivisaspt1.R

data class RegistroDivisa(val fecha: String, val base: String, val tasa: String)

class DivisaAdapter(private val lista: List<RegistroDivisa>) :
    RecyclerView.Adapter<DivisaAdapter.DivisaViewHolder>() {

    class DivisaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fechaText: TextView = itemView.findViewById(R.id.fechaText)
        val baseText: TextView = itemView.findViewById(R.id.baseText)
        val tasaText: TextView = itemView.findViewById(R.id.tasaText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivisaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_divisa, parent, false)
        return DivisaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DivisaViewHolder, position: Int) {
        val item = lista[position]
        holder.fechaText.text = item.fecha
        holder.baseText.text = "Base: ${item.base}"
        holder.tasaText.text = "Tasa: $item.tasa"
    }

    override fun getItemCount() = lista.size
}
