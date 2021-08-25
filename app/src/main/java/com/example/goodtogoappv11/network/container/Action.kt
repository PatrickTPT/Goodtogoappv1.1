package com.example.goodtogoappv11.network.container

data class Action(
    val _id: String,
    val boxAction: String,
    val boxStatus: String,
    val phone: String,
    val stationID: StationID,
    val timestamps: String
)