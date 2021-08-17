package com.example.goodtogoappv11.network

import java.io.Serializable

data class Role(
    val apiKey: String,
    val asStationID: Int,
    val asStoreID: Any,
    val boxable: Boolean,
    val group: String,
    val manager: Boolean,
    val roleType: String,
    val secretKey: String,
    val stationID: Int,
    val stationName: String,
    val storeID: Int,
    val storeName: String
): Serializable