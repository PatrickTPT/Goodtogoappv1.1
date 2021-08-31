package com.example.goodtogoappv11.network.readytoclean

data class ReadyToCleanResponse(
    val code: String,
    val errorDict: List<ErrorDict>,
    val errorList: List<List<Int>>,
    val listExplanation: List<String>,
    val message: String,
    val stateExplanation: List<String>,
    val type: String
)