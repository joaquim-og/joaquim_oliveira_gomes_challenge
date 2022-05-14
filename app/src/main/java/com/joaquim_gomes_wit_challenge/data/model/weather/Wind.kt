package com.joaquim_gomes_wit_challenge.data.model.weather

import com.google.gson.annotations.SerializedName

data class Wind(

    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val deg: Int
)