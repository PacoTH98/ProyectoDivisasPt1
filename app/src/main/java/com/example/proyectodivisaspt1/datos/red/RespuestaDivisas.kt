package com.example.proyectodivisaspt1.red

import com.google.gson.annotations.SerializedName

data class RespuestaDivisas(
    val result: String,

    @SerializedName("base_code")
    val base: String,

    @SerializedName("time_last_update_utc")
    val date: String,

    @SerializedName("conversion_rates")
    val rates: Map<String, Double>
)
