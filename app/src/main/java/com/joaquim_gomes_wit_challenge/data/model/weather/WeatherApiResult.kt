package com.joaquim_gomes_wit_challenge.data.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherApiResult(
    @SerializedName("coord")
    val coord: Coord,

    @SerializedName("weather")
    val weather: ArrayList<Weather>,

    @SerializedName("base")
    val base: String,

    @SerializedName("main")
    val main: Main,

    @SerializedName("visibility")
    val visibility: Int,

    @SerializedName("wind")
    val wind: Wind,

    @SerializedName("clouds")
    val clouds: Clouds,

    @SerializedName("dt")
    val dt: Int,

    @SerializedName("sys")
    val sys: Sys,

    @SerializedName("timezone")
    val timezone: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("cod")
    val cod: Int
)