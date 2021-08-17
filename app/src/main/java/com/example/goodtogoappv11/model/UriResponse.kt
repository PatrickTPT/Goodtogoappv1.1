package com.example.goodtogoappv11.model

import java.io.Serializable


data class UriResponse(
    val token: String,
    val uri: String
): Serializable