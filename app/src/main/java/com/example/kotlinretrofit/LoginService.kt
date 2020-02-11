package com.example.kotlinretrofit

import retrofit2.Call
import retrofit2.http.*

interface LoginService {

    @FormUrlEncoded
    @POST("/auth/register")
    fun requestLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Auth>

    @GET("spec/getContents")
    fun getContents(
        @Query("category") category:String,
        @Query("activityName") activityName: String
    ): Call<Spec>
}