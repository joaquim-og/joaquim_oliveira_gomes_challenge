package com.joaquim_gomes_wit_challenge.data.model.weather

data class ScreenWeatherInfo(

    var date: String? = null,

    var nameCity: String? = null,

    var temperatureActual: String? = null,

    var icon: String? = null,

    var humidity: String? = null,

    var descriptionWeather: String? = null,

    var temperatureMin: String? = null,

    var temperatureMax: String? = null,

    var pressure: String? = null,

    var visibility: String? = null,

    var windSpeed: String? = null,

    var lat: Double? = 0.0,

    var lng: Double? = 0.0

)
