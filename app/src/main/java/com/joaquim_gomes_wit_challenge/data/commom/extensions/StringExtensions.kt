package com.joaquim_gomes_wit_challenge.data.commom.extensions

fun String.mapWeatherIcon(): String {
    return when (this) {
        "01d" -> "sunny.json"
        "01n" -> "moon.json"
        "02d" -> "suncloud.json"
        "02n" -> "mooncloud.json"
        "03d" -> "clouds.json"
        "03n" -> "clouds.json"
        "04d" -> "sunbrokenclouds.json"
        "04n" -> "moonbrokenclouds.json"
        "09d" -> "showerrain.json"
        "09n" -> "showerrain.json"
        "10d" -> "rain.json"
        "10n" -> "rain.json"
        "11d" -> "thunderstorm.json"
        "11n" -> "thunderstorm.json"
        "13d" -> "snow.json"
        "13n" -> "snow.json"
        "50d" -> "mist.json"
        "50n" -> "mist.json"
        else -> ""
    }
}