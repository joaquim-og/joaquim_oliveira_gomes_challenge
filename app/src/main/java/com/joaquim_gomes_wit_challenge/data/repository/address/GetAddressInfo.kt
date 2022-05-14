package com.joaquim_gomes_wit_challenge.data.repository.address

import com.google.android.gms.location.FusedLocationProviderClient

interface GetAddressInfo {

    fun getDeviceLocation(fusedLocationClient: FusedLocationProviderClient)

}