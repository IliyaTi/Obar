package com.example.obar.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obar.data.ObarRetrofit
import com.example.obar.model.UserLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListDataScreenViewModel(

) : ViewModel() {

    var items = mutableStateListOf<UserLocal>()

    var error by mutableStateOf("")
    var loading by mutableStateOf(false)


    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            loading = true
            try {
                val response = ObarRetrofit.api.getAddresses()

                if (response.isSuccessful) {
                    items.addAll(response.body() ?: listOf())
                } else {
                    if (response.code() in 400..499) {
                        error = "خطایی در سمت شما رخ داد"
                    } else if (response.code() in 500..599) {
                        error = "خطایی در سرور رخ داد"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                error = "ارتباط برقرار نشد"
            } finally {
                loading = false
            }



        }
    }




}