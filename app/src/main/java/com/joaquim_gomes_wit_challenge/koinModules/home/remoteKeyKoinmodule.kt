package com.joaquim_gomes_wit_challenge.koinModules.home

import com.google.firebase.firestore.FirebaseFirestore
import com.joaquim_gomes_wit_challenge.data.network.credentials.RemoteDataSourceKeys
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectRemoteKeysInfoKoinModule() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            RemoteDataSourceApiKeys
        )
    )
}

val RemoteDataSourceApiKeys: Module = module {
    single { RemoteDataSourceKeys(FirebaseFirestore.getInstance()) }
}