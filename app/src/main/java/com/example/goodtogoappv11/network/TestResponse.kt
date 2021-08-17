package com.example.goodtogoappv11.network

import java.io.Serializable

data class TestResponse(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int

): Serializable