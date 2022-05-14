package com.joaquim_gomes_wit_challenge.data.network.weather_api

import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInfoApi {

    @GET("/data/2.5/weather?")
    fun getWeatherInfoByLatLong(
        @Query("lat") lat: String,
        @Query("lon") lng: String,
        @Query("appid") appId: String,
        @Query("lang") lang: String,
        @Query("units") units: String,
    ): Call<WeatherApiResult>

}