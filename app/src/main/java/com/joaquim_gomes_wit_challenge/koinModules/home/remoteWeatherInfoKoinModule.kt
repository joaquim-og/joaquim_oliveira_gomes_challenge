package com.joaquim_gomes_wit_challenge.koinModules.home

import com.joaquim_gomes_wit_challenge.data.network.weather_api.RemoteDataSourceWeatherInfo
import com.joaquim_gomes_wit_challenge.data.repository.weather.GetWeatherInfoImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectRemoteWeatherInfoKoinModule() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            RemoteWeatherInfo, RepositoryGetWeatherInfo
        )
    )
}

val RemoteWeatherInfo: Module = module {
    single { RemoteDataSourceWeatherInfo() }
}

val RepositoryGetWeatherInfo: Module = module {
    single { GetWeatherInfoImpl(get(), get(), get()) }
}