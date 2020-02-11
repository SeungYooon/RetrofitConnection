package com.example.kotlinretrofit

import retrofit2.Call
import retrofit2.http.*

interface API {

    @GET("/auth/login")
    fun logIn(@Query("email") email: String, @Query("password") password: String): Call<Auth>


//    @FormUrlEncoded
//    @POST("/auth/login")
//    fun logIn(@Field("email") email: String, @Field("password") password: String): Call<Auth>

    @FormUrlEncoded
    @POST("/auth/register")
    fun SignUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("age") age: Int
    ): Call<Auth>
}