package com.joaquim_gomes_wit_challenge.data.commom.extensions

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.joaquim_gomes_wit_challenge.data.model.googleMaps.GoogleMapsObjects

/**
 * set new marker location within googleMap instance.
 */

fun GoogleMap.setMarkerLocation(
    googleMap: GoogleMap,
    userLatLong: LatLng?,
    cityName: String? = null
) {
    googleMap.clear()

    if (userLatLong != null) {

        googleMap.addMarker(
            MarkerOptions().position(userLatLong)
        )

        if (!cityName.isNullOrEmpty()) {
            googleMap.addMarker(
                MarkerOptions().position(userLatLong).title(cityName)
            )
        }

        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                userLatLong,
                GoogleMapsObjects.MAPS_ZOOM_STREETS
            )
        )

    }
}