package com.joaquim_gomes_wit_challenge.data.repository.weather

import com.google.android.gms.location.FusedLocationProviderClient
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo

interface GetWeatherInfo {

    fun getWeatherInfoByLatLong(
        fusedLocationClient: FusedLocationProviderClient,
        weatherData: (MutableList<ScreenWeatherInfo>) -> Unit
    )

}