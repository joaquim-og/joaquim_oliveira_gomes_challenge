package com.joaquim_gomes_wit_challenge.koinModules.commom

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import com.joaquim_gomes_wit_challenge.MyApplication.Companion.globalContext
import com.joaquim_gomes_wit_challenge.data.commom.CheckPermissions
import com.joaquim_gomes_wit_challenge.data.commom.Decoder
import com.joaquim_gomes_wit_challenge.data.commom.SetToastMessage
import com.joaquim_gomes_wit_challenge.data.commom.network.VerifyNetwork
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectCommonClassesModule() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            verifyNetworkStatus,
            setToastMessageModule,
            checkPermissions,
            decoder
        )
    )
}

val verifyNetworkStatus: Module = module {
    single {
        VerifyNetwork(
            ContextCompat.getSystemService(
                globalContext,
                ConnectivityManager::class.java
            )
        )
    }
}

val setToastMessageModule: Module = module {
    single { SetToastMessage() }
}

val checkPermissions: Module = module {
    single { CheckPermissions() }
}

val decoder: Module = module {
    single { Decoder() }
}