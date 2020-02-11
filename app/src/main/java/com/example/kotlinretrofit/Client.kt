package com.example.kotlinretrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Client {
    var retrofitService: API

   // val BASE_URL: String = "https://thisisyourbackend.kr"
    val BASE_URL: String = "http://192.168.123.103:3000"

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val logger =
            OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(logger)
            .build()

        retrofitService = retrofit.create(API::class.java)

    }
}