package com.example.goodtogoappv11.network


import com.example.goodtogoappv11.network.readytoclean.ReadyToCleanResponse
import com.example.goodtogoappv11.network.reload.ReloadResponse
import com.example.goodtogoappv11.network.returnobject.ReturnResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ourService {

    /** Success */
    @POST("users/login")
    fun postLogin(
        @Header("reqId")reqId: String,
        @Header("reqTime")reqTime: String,
        @Body body: JsonObject
        ): Call<LoginResponse>

    /** Success */
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


    /** Success */
    @GET("containers/get/list")
    fun containersGetList(
        @Header("reqId")reqId: String,
        @Header("reqTime")reqTime: String
    ) :Call<ContainerGetListResponse>


    /** Success */
    @GET("stores/list")
    fun getStoresList(
        @Header("reqId")reqId: String,
        @Header("reqTime")reqTime: String
    ) :Call<StoresListResponse>


    @GET("stores/boxToSign")
    fun getBoxToSignList(
        @Header("Authorization")Authorization: String,
        @Header("ApiKey") ApiKey: String
    ) :Call<BoxToSignListResponse>

    /** Success */
    @GET("deliveryList/reloadHistory")
    fun getReloadList(
        @Header("Authorization") Authorization: String,
        @Header("ApiKey") ApiKey: String
    ) :Call<ReloadResponse>

    /** Success */
    @POST("containers/readyToClean/list")
    fun postReadyToClean(
        @Header("Authorization") Authorization: String,
        @Header("ApiKey") ApiKey: String,
        @Body body: JsonObject
    ) :Call<ReadyToCleanResponse>
    //path example: @Path("container") container:String

    /** Success */
    @POST("containers/return/list")
    fun postReturn(
        @Header("Authorization") Authorization: String,
        @Header("ApiKey") ApiKey: String,
        @Body body: JsonObject
    ) :Call<ReturnResponse>







    //test REST API
    @GET("posts/1")
    fun testConnection(
        @Header("AAA") AAA:Int,
        @Header("BBB") BBB:String
    ) :Call<TestResponse>

}