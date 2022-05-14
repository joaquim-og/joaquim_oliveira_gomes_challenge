package com.joaquim_gomes_wit_challenge.koinModules.home

import com.joaquim_gomes_wit_challenge.data.repository.address.GetAddressInfoImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectAddressLatLngKoinModule() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            RepositoryGetAddressInfo
        )
    )
}

val RepositoryGetAddressInfo: Module = module {
    single { GetAddressInfoImpl() }
}