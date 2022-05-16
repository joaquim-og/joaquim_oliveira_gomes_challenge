package com.joaquim_gomes_wit_challenge.data.network.credentials

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.joaquim_gomes_wit_challenge.data.commom.SharedPrefs
import com.joaquim_gomes_wit_challenge.data.model.remoteDbKeys
import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherInfoObjects.WEATHER_API_KEY
import kotlinx.coroutines.tasks.await

class RemoteDataSourceKeys(
    private val remoteDB: FirebaseFirestore
) {

    lateinit var data: MutableList<remoteDbKeys>

    suspend fun getKeysItems():
            List<remoteDbKeys> {

        data = mutableListOf()

        data.addAll(searchRemoteApisFirebase())

        return data

    }

    private suspend fun searchRemoteApisFirebase(): List<remoteDbKeys> {

        return try {
            remoteDB.collection("keys")
                .get()
                .await()
                .documents
                .mapNotNull {
                    val keysItems = Gson().toJson(it.data)
                    val remoteInfo =
                        GsonBuilder().create().fromJson(keysItems, remoteDbKeys::class.java)
                    saveKeysLocal(remoteInfo)

                    remoteInfo
                }
        } catch (e: Exception) {
            Log.d(
                "Error get Keys",
                "Error on get Keys, here is the why -> ${e.localizedMessage}"
            )
            emptyList<remoteDbKeys>()
        }

    }

    private fun saveKeysLocal(remoteInfo: remoteDbKeys?) {

        when (remoteInfo?.apiName.toString()){
            WEATHER_API_KEY -> SharedPrefs.setApiKeyData(WEATHER_API_KEY, remoteInfo?.apiKey.toString())
        }

    }

}