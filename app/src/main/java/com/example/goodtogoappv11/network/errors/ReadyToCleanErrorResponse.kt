package com.example.goodtogoappv11.network.errors

data class ReadyToCleanErrorResponse(
    val code: String,
    val errorDict: List<ErrorDict>,
    val errorList: List<List<Int>>,
    val listExplanation: List<String>,
    val message: String,
    val stateExplanation: List<String>,
    val type: String
)