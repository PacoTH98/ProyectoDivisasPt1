package com.example.proyectodivisaspt1.datos.proveedor

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.proyectodivisaspt1.datos.base.DivisaBD

class ProveedorDivisas : ContentProvider() {

    companion object {
        const val AUTH = "com.example.proyectodivisaspt1.divisas"
        val URI_DIVISAS: Uri = Uri.parse("content://$AUTH/divisas")
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val contexto = context ?: return null
        val dao = DivisaBD.obtenerBD(contexto).obtenerDAO()
        val divisas = dao.obtenerTodas()

        val cursor = MatrixCursor(arrayOf("id", "fecha", "base", "tasasJson"))

        for (divisa in divisas) {
            cursor.addRow(arrayOf(divisa.id, divisa.fecha, divisa.base, divisa.tasasJson))
        }

        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?) = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0
    override fun getType(uri: Uri): String = "vnd.android.cursor.dir/$AUTH.divisas"
}
