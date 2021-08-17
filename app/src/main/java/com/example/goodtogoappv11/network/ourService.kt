package com.example.goodtogoappv11.network

import com.example.goodtogoappv11.model.UriResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ourService {

    @POST("users/login")
    fun postLogin(
        @Header("reqID") reqId: String,
        @Header("reqTime") reqTime: String,
        @Query("phone") phone: String,
        @Query("password") password: String?
        ): Call<LoginResponse>

    @POST("users/logout")
    fun postLogout(
        @Header("Authorization") Authorization: String,
        @Header("ApiKey") ApiKey: String
    ): Call<LogoutResponse>

    @GET("containers/challenge/token")
    fun getContainersChallengeToken(
        @Header("Authorization")Authorization: String,
        @Header("ApiKey") ApiKey: String
    ): Call<UriResponse>


    @GET("posts/1")
    fun testConnection(
        @Header("AAA") AAA:Int,
        @Header("BBB") BBB:String
    ) :Call<TestResponse>

}