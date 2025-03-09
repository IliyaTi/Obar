package com.example.obar.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obar.data.ObarRetrofit
import com.example.obar.model.UserRemote
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetDataScreenViewModel : ViewModel() {

    val genderOptions = listOf(Pair("Male", "آقا"), Pair("Female", "خانم"))

    var loading by mutableStateOf(false)
    var error by mutableStateOf("")

    val campos = CameraPositionState(position = CameraPosition.fromLatLngZoom(LatLng(35.7094165, 51.3700919), 12f))
    var step by mutableStateOf(1)

    var firstName by mutableStateOf("")
    var firstNameValid by mutableStateOf(Pair(true, ""))
    var lastName by mutableStateOf("")
    var lastNameValid by mutableStateOf(Pair(true, ""))
    var mobileNumber by mutableStateOf("")
    var mobileNumberValid by mutableStateOf(Pair(true, ""))
    var phoneNumber by mutableStateOf("")
    var phoneNumberValid by mutableStateOf(Pair(true, ""))
    var address by mutableStateOf("")
    var addressValid by mutableStateOf(Pair(true, ""))
    var gender by mutableStateOf(genderOptions[0])



    fun saveToRemote(onSuccess: () -> Unit) {
        if (!validateInput()) return
        loading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ObarRetrofit.api.setAddress(
                    UserRemote(
                        region = 1,
                        address = address,
                        lat = campos.position.target.latitude,
                        lng = campos.position.target.longitude,
                        coordinate_mobile = mobileNumber,
                        coordinate_phone_number = phoneNumber,
                        first_name = firstName,
                        last_name = lastName,
                        gender = gender.first
                    )
                )

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) { onSuccess() }
                } else {
                    if (response.code() in 400..499) error = "خطا از جانب شما رخ داده. لطفا ورودی های خود را کنترل کنید."
                    else if (response.code() in 500..599) error = "مشکلی در سمت سرورها رخ داد"
                    return@launch
                }

            } catch (e: Exception) {
                e.printStackTrace()
                error = "ارتباط برقرار نشد"
            } finally {
                loading = false
            }
        }
    }


    fun validateInput(): Boolean {
        var result = true
        if (firstName.length <= 2) {
            firstNameValid = Pair(false, "این فیلد نمیتواند خالی باشد")
            result = false
        } else firstNameValid = Pair(true, "")
        if (lastName.length <= 2) {
            lastNameValid = Pair(false, "این فیلد نمیتواند خالی باشد")
            result = false
        } else lastNameValid = Pair(true, "")
        if (mobileNumber.length != 11) {
            mobileNumberValid = Pair(false, "شماره باید 11 رقم باشد")
            result = false
        } else mobileNumberValid = Pair(true, "")
        if (phoneNumber.length != 11) {
            phoneNumberValid = Pair(false, "شماره باید 11 رقم باشد")
            result = false
        } else phoneNumberValid = Pair(true, "")
        if (address.length <= 2) {
            addressValid = Pair(false, "این فیلد نمیتواند خالی باشد")
            result = false
        } else addressValid = Pair(true, "")

        return result
    }


}