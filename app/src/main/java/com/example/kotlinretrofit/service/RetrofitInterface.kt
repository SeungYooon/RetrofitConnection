package com.example.kotlinretrofit.service

import com.example.kotlinretrofit.dto.Back
import com.example.kotlinretrofit.dto.User

import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    @GET("/question")
    fun requestAllData(): Call<ArrayList<Back>>

    @GET("/question")
    fun test(): Call<ArrayList<Back>>

    @GET("/question")
    fun test2(): Call<ArrayList<Back>>

    @GET("/api/users")
    fun api(): Call<ArrayList<User>>


}