package com.joaquim_gomes_wit_challenge

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.joaquim_gomes_wit_challenge.data.commom.inAppFirebaseAnalytics.InAppFirebaseAnalytics.firebaseAnalytics
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
        globalContext = this

        initKoin()
        initInAppFirebaseAnalytics()
        appChecker()
    }

    private fun initInAppFirebaseAnalytics() {
        firebaseAnalytics = Firebase.analytics
    }


    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }
    }

    private fun appChecker() {
        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )
    }

}