package com.example.goodtogoappv11.network

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ourService {

    @POST("users/login")
    fun postLogin(
        @Header("reqID") reqId: String,
        @Header("reqTime") reqTime: String,
        @Body() body: JSONObject
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

    //verified!
    @GET("containers/get/list")
    fun containersGetList(
        @Header("reqId")reqId: String,
        @Header("reqTime")reqTime: String
    ) :Call<ContainerGetListResponse>

    //verified!
    @GET("stores/list")
    fun getStoreslist(
        @Header("reqId")reqId: String,
        @Header("reqTime")reqTime: String
    ) :Call<StoresListResponse>


    @GET("stores/boxToSign")
    fun getBoxToSignList(
        @Header("Authorization")Authorization: String,
        @Header("ApiKey") ApiKey: String
    ) :Call<BoxToSignListResponse>





    @GET("posts/1")
    fun testConnection(
        @Header("AAA") AAA:Int,
        @Header("BBB") BBB:String
    ) :Call<TestResponse>

}