package com.example.goodtogoappv11.network

import java.io.Serializable

data class LogoutResponse(
    val type: String,
    val message: String,
): Serializable
