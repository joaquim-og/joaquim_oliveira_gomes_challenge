package com.joaquim_gomes_wit_challenge.data.repository.weather

import com.google.android.gms.location.FusedLocationProviderClient

interface GetWeatherInfo {

    fun getWeatherInfoByLatLong(fusedLocationClient: FusedLocationProviderClient)

}