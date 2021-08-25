package com.example.goodtogoappv11.network

import java.io.Serializable


data class UriResponse(
    val token: String,
    val uri: String
): Serializable