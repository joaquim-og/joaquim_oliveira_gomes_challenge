package com.joaquim_gomes_wit_challenge.koinModules.home

import com.joaquim_gomes_wit_challenge.data.network.weather_api.RemoteDataSourceWeatherInfo
import com.joaquim_gomes_wit_challenge.data.repository.weather.GetWeatherInfoImpl
import com.joaquim_gomes_wit_challenge.views.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectHomeModule() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule
        )
    )
}

val viewModelModule: Module = module {
    viewModel {
        HomeViewModel(get())
    }
}