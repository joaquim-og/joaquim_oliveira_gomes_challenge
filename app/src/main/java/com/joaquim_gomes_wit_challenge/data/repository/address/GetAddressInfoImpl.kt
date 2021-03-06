package com.joaquim_gomes_wit_challenge.data.repository.address

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.joaquim_gomes_wit_challenge.data.model.address.AddressInfoObjects.ERROR_CALL_API_LAT_LNG

class GetAddressInfoImpl() : GetAddressInfo {

    override fun getDeviceLocation(
        fusedLocationClient: FusedLocationProviderClient,
        userLatLng: (LatLng) -> Unit
    ) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    userLatLng(
                        LatLng(
                            location.latitude,
                            location.longitude
                        )
                    )
                }
            }

        } catch (e: SecurityException) {
            Log.d(
                ERROR_CALL_API_LAT_LNG,
                "error on get device location, this why -> ${e.localizedMessage} | ${e.stackTrace}"
            )
        }
    }

}
