package com.example.obar.data

import com.example.obar.model.UserLocal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AcharehApi {

    @GET("address")
    suspend fun getAddresses(): Response<List<UserLocal>>

    @POST("address")
    suspend fun setAddress()


}