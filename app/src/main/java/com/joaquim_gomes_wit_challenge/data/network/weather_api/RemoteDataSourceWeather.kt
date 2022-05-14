package com.joaquim_gomes_wit_challenge.data.network.weather_api

import com.google.android.gms.maps.model.LatLng
import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherApiResult
import com.joaquim_gomes_wit_challenge.data.network.weather_api.WeatherInfoObjects.WEATHER_API_CALL_LANG
import com.joaquim_gomes_wit_challenge.data.network.weather_api.WeatherInfoObjects.WEATHER_API_CALL_UNITS
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceWeatherInfo {

    private val retrofitClient: Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val endpoint = retrofitClient.create(WeatherInfoApi::class.java)

    fun getWeatherInfoByPassingLatLong(
        latLng: LatLng,
        appId: String
    ): Call<WeatherApiResult> =
        endpoint.getWeatherInfoByLatLong(
            latLng.latitude.toString(),
            latLng.longitude.toString(),
            appId,
            WEATHER_API_CALL_LANG,
            WEATHER_API_CALL_UNITS
        )

}