package com.joaquim_gomes_wit_challenge.data.model.weather

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon")
    val lon: Double,

    @SerializedName("lat")
    val lat: Double
)