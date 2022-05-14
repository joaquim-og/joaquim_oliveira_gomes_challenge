package com.joaquim_gomes_wit_challenge.data.model.weather

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)