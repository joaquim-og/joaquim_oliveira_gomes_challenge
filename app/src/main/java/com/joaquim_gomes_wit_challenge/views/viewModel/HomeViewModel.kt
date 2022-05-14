package com.joaquim_gomes_wit_challenge.views.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo
import com.joaquim_gomes_wit_challenge.data.repository.weather.GetWeatherInfoImpl

class HomeViewModel(private val repositoryGetWeatherInfo: GetWeatherInfoImpl) : ViewModel() {

    private val _actualWeatherInfo = repositoryGetWeatherInfo.weatherInfo
    val actualWeatherInfo: LiveData<ScreenWeatherInfo?> get() = _actualWeatherInfo

    fun getWeatherData(fusedLocationClient: FusedLocationProviderClient) {
        repositoryGetWeatherInfo.getWeatherInfoByLatLong(fusedLocationClient)
    }

    fun getWeatherIcon(icon: String?): String = repositoryGetWeatherInfo.mapWeatherIcon(icon)

}