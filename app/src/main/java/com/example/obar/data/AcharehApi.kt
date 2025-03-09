package com.example.obar.data

import com.example.obar.model.UserLocal
import com.example.obar.model.UserRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AcharehApi {

    @GET("api/karfarmas/address")
    suspend fun getAddresses(): Response<List<UserLocal>>

    @POST("api/karfarmas/address")
    suspend fun setAddress(@Body user: UserRemote): Response<Unit>


}