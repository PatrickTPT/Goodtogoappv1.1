package com.example.goodtogoappv11.network.readytoclean

data class ErrorDict(
    val containerID: Int,
    val newState: Int,
    val originalState: Int
)