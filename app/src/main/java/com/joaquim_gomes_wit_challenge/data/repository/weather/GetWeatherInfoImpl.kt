package com.joaquim_gomes_wit_challenge.data.repository.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.analytics.ktx.logEvent
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.joaquim_gomes_wit_challenge.MyApplication.Companion.globalContext
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.Decoder
import com.joaquim_gomes_wit_challenge.data.commom.SharedPrefs
import com.joaquim_gomes_wit_challenge.data.commom.extensions.toDateTime
import com.joaquim_gomes_wit_challenge.data.commom.inAppFirebaseAnalytics.InAppFirebaseAnalytics.ER_GET_WEATHER
import com.joaquim_gomes_wit_challenge.data.commom.inAppFirebaseAnalytics.InAppFirebaseAnalytics.GET_WEATHER_DATA
import com.joaquim_gomes_wit_challenge.data.commom.inAppFirebaseAnalytics.InAppFirebaseAnalytics.firebaseAnalytics
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo
import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherApiResult
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.BERLIN_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.COPENHAGEN_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.DUBLIN_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.LISBON_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.LONDON_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.MADRID_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.PARIS_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.PRAGUE_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.ROME_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.VIENNA_LAT_LONG
import com.joaquim_gomes_wit_challenge.data.network.weather_api.RemoteDataSourceWeatherInfo
import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherInfoObjects.ERROR_CALL_API_WEATHER
import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherInfoObjects.STATUS_OK
import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherInfoObjects.WEATHER_API_KEY
import com.joaquim_gomes_wit_challenge.data.repository.address.GetAddressInfoImpl
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetWeatherInfoImpl(
    private val remoteDataSourceWeatherInfo: RemoteDataSourceWeatherInfo,
    private val repositoryGetAddressInfo: GetAddressInfoImpl,
    private val decoder: Decoder,
) : GetWeatherInfo {

    private val _errorOnGetWeather = MutableLiveData<Boolean?>()
    val errorOnGetWeather: LiveData<Boolean?> get() = _errorOnGetWeather

    private val _weatherInfo = MutableLiveData<List<ScreenWeatherInfo?>>()
    val weatherInfo: LiveData<List<ScreenWeatherInfo?>> get() = _weatherInfo

    private val _userLocalLatLng = repositoryGetAddressInfo.userLocalLatLng

    override fun getWeatherInfoByLatLong(fusedLocationClient: FusedLocationProviderClient) {

        CoroutineScope(Dispatchers.IO).launch {

            repositoryGetAddressInfo.getDeviceLocation(
                fusedLocationClient,
                userLatLng = { userLatLng ->

                    val listOfWeatherData = mutableListOf<ScreenWeatherInfo>()

                    val listOfCities = listOf(
                        userLatLng,
                        LISBON_LAT_LONG,
                        PARIS_LAT_LONG,
                        MADRID_LAT_LONG,
                        BERLIN_LAT_LONG,
                        COPENHAGEN_LAT_LONG,
                        ROME_LAT_LONG,
                        LONDON_LAT_LONG,
                        DUBLIN_LAT_LONG,
                        PRAGUE_LAT_LONG,
                        VIENNA_LAT_LONG
                    )

                    listOfCities.forEach {
                        getWeatherData(it, weatherData = { cityWeather ->
                            listOfWeatherData.add(cityWeather)
                            _weatherInfo.postValue(listOfWeatherData)
                        })
                    }

                })
        }
    }

    private fun getWeatherData(latLng: LatLng, weatherData: (ScreenWeatherInfo) -> Unit) {

        val getWeatherResult = remoteDataSourceWeatherInfo.getWeatherInfoByPassingLatLong(
            latLng,
            decoder.decoder(SharedPrefs.getApiKeyData(WEATHER_API_KEY))
        )

        getWeatherResult.enqueue(object : Callback<WeatherApiResult> {

            override fun onResponse(
                call: Call<WeatherApiResult>,
                response: Response<WeatherApiResult>
            ) {

                try {
                    if (response.isSuccessful) {

                        val responseJSON = Gson().toJson(response.body())

                        if (responseJSON.isNotEmpty()) {
                            val responseData = GsonBuilder().create()
                                .fromJson(responseJSON, WeatherApiResult::class.java)

                            if (responseData.cod == STATUS_OK) {
                                responseData.let {
                                    val weatherInfo = ScreenWeatherInfo(
                                        nameCity = it.name,
                                        temperatureActual = it.main.temp.toString(),
                                        icon = it.weather[0].icon,
                                        humidity = it.main.humidity.toString(),
                                        descriptionWeather = it.weather[0].description,
                                        date = it.dt.toDateTime(),
                                        temperatureMin = it.main.temp_min.toString(),
                                        temperatureMax = it.main.temp_max.toString(),
                                        pressure = it.main.pressure.toString(),
                                        visibility = it.visibility.toString(),
                                        windSpeed = it.wind.speed.toString(),
                                        lat = it.coord.lat,
                                        lng = it.coord.lon
                                    )

                                    weatherData(weatherInfo)

                                    registerFirebaseWeatherLogs(true, responseData.cod.toString())
                                }
                            } else {
                                registerFirebaseWeatherLogs(false, responseData.cod.toString())
                            }

                        } else {
                            _errorOnGetWeather.postValue(true)
                            registerFirebaseWeatherLogs(
                                false,
                                globalContext.getString(R.string.error_empty_response_weather_data)
                            )
                        }
                    } else {
                        _errorOnGetWeather.postValue(true)
                        registerFirebaseWeatherLogs(
                            false,
                            globalContext.getString(R.string.error_response_not_successful_weather_data)
                        )
                    }
                } catch (exception: Throwable) {
                    exception.printStackTrace()
                    Log.d(
                        ERROR_CALL_API_WEATHER,
                        "exception on api response, this why -> ${exception.localizedMessage} | ${exception.stackTrace}"
                    )
                    registerFirebaseWeatherLogs(false, exception.localizedMessage)
                }
            }

            override fun onFailure(call: Call<WeatherApiResult>, e: Throwable) {
                e.printStackTrace()
                Log.d(
                    ERROR_CALL_API_WEATHER,
                    "error on api response, this why -> ${e.localizedMessage} | ${e.stackTrace}"
                )
                registerFirebaseWeatherLogs(false, e.localizedMessage)
            }

        })
    }

    fun mapWeatherIcon(newWeatherDataIcon: String?): String {

        return when (newWeatherDataIcon) {
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

    private fun registerFirebaseWeatherLogs(successfully: Boolean, stacktrace: String?) {

        firebaseAnalytics?.logEvent(
            GET_WEATHER_DATA
        ) {
            if (successfully) {
                param(
                    "$GET_WEATHER_DATA successfully",
                    GET_WEATHER_DATA
                )
            } else {
                param(
                    ER_GET_WEATHER,
                    "Stack trace -> $stacktrace"
                )
            }
        }
    }

}