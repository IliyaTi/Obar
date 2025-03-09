package com.example.obar.data

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ObarRetrofit {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .callTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val newBuilder = chain.request().newBuilder()
                .header("Authorization", Credentials.basic("09822222222", "Sana12345678"))
                .header("Content-Type", "application/json")

            chain.proceed(newBuilder.build())
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://stage.achareh.ir/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(AcharehApi::class.java)

}