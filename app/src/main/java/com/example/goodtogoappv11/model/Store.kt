package com.example.goodtogoappv11.model

import java.io.Serializable

data class Store(
    val name : String?,
    val id : Int?,
    val type : Int?,
    val viewType : Int
): Serializable
