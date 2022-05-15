package com.joaquim_gomes_wit_challenge.views.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo
import com.joaquim_gomes_wit_challenge.data.network.credentials.RemoteDataSourceKeys
import com.joaquim_gomes_wit_challenge.data.repository.weather.GetWeatherInfoImpl
import kotlinx.coroutines.*

class HomeViewModel(
    private val repositoryGetWeatherInfo: GetWeatherInfoImpl,
    private val remoteKeysRepository: RemoteDataSourceKeys
) : ViewModel() {

    private val _actualWeatherInfo = repositoryGetWeatherInfo.weatherInfo
    val actualWeatherInfo: LiveData<List<ScreenWeatherInfo?>> get() = _actualWeatherInfo

    private val _selectedCityWeatherData = MutableLiveData<ScreenWeatherInfo?>()
    val selectedCityWeatherData: LiveData<ScreenWeatherInfo?> get() = _selectedCityWeatherData

    fun getWeatherData(fusedLocationClient: FusedLocationProviderClient) {
        repositoryGetWeatherInfo.getWeatherInfoByLatLong(fusedLocationClient)
    }

    fun getWeatherIcon(icon: String?): String = repositoryGetWeatherInfo.mapWeatherIcon(icon)

    fun getApiKeys() {
        CoroutineScope(Dispatchers.IO).launch {
            val res = async { remoteKeysRepository.getKeysItems() }
            val listItems = res.await()
            withContext(Dispatchers.IO) {
                listItems.let {
                }
            }
        }
    }

    fun setSelectedCity(cityWeatherData: ScreenWeatherInfo) {
        clearSelectedCityWeatherData()
        _selectedCityWeatherData.postValue(cityWeatherData)
    }

    private fun clearSelectedCityWeatherData() {
        _selectedCityWeatherData.postValue(null)
    }

}