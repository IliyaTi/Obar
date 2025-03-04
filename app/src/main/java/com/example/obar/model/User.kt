package com.example.obar.model

import com.google.android.gms.maps.model.LatLng

data class UserRemote(
    val region: Int,
    val address: String,
    val lat: Double,
    val lng: Double,
    val coordinate_mobile: String,
    val coordinate_phone_number: String,
    val first_name: String,
    val last_name: String,
    val gender: String
)


data class UserLocal(
    val address: String,
    val first_name: String,
    val last_name: String,
    val coordinate_mobile: String
)
