package com.joaquim_gomes_wit_challenge.data.repository.address

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng

interface GetAddressInfo {

    fun getDeviceLocation(
        fusedLocationClient: FusedLocationProviderClient,
        userLatLng: (LatLng) -> Unit
    )

}