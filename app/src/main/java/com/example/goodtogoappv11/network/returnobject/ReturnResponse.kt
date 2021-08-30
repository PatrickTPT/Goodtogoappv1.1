package com.example.goodtogoappv11.network.returnobject

data class ReturnResponse(
    val containerList: List<Container>,
    val message: String,
    val oriUser: String,
    val type: String
)