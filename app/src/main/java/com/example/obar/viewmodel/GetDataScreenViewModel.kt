package com.example.obar.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.obar.data.ObarRetrofit
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

class GetDataScreenViewModel : ViewModel() {

    val campos = CameraPositionState(position = CameraPosition.fromLatLngZoom(LatLng(35.7094165, 51.3700919), 12f))
    var step by mutableStateOf(1)

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var mobileNumber by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var address by mutableStateOf("")
    var gender by mutableIntStateOf(1) // 0,f / 1,m


    fun saveToRemote() {
        ObarRetrofit.api.
    }


}