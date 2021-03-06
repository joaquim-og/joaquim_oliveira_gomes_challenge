package com.joaquim_gomes_wit_challenge

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.joaquim_gomes_wit_challenge.data.commom.SharedPrefs
import com.joaquim_gomes_wit_challenge.data.commom.inAppFirebaseAnalytics.InAppFirebaseAnalytics.firebaseAnalytics
import com.joaquim_gomes_wit_challenge.koinModules.commom.injectCommonClassesModule
import com.joaquim_gomes_wit_challenge.koinModules.home.injectAddressLatLngKoinModule
import com.joaquim_gomes_wit_challenge.koinModules.home.injectHomeModule
import com.joaquim_gomes_wit_challenge.koinModules.home.injectRemoteKeysInfoKoinModule
import com.joaquim_gomes_wit_challenge.koinModules.home.injectRemoteWeatherInfoKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    companion object {
        @get:Synchronized
        lateinit var globalContext: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        globalContext = this@MyApplication

        initKoin()
        initPrefs()
        initInAppFirebaseAnalytics()
        appChecker()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }

        injectCommonClassesModule()
        injectRemoteKeysInfoKoinModule()
        injectRemoteWeatherInfoKoinModule()
        injectAddressLatLngKoinModule()
        injectHomeModule()
    }

    private fun initPrefs() {
        SharedPrefs.getPrefs(globalContext)
    }

    private fun initInAppFirebaseAnalytics() {
        firebaseAnalytics = Firebase.analytics
    }

    private fun appChecker() {
        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )
    }

}