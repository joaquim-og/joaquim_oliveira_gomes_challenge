package com.joaquim_gomes_wit_challenge.data.commom.inAppFirebaseAnalytics

import com.google.firebase.analytics.FirebaseAnalytics

object InAppFirebaseAnalytics {

    var firebaseAnalytics: FirebaseAnalytics? = null

    //Weather feature
    const val GET_WEATHER_DATA = "Get_weather_api_data"
    const val ER_GET_WEATHER = "Error_get_weather_data"

}