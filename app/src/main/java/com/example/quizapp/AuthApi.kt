package com.example.quizapp

import com.example.quizapp.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("login") login : String,
        @Field("password") password : String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/user/createUser")
    fun register(
        @Field("login") login : String,
        @Field("password") password : String
    ): Call<LoginResponse>
}