package com.example.goodtogoappv11.network

data class ErrorResponse (

    val code: String, // ex.A001
    val type : String, // Error Type
    val message : String,
    val extra : Any?

        )