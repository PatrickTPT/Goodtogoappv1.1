package com.example.goodtogoappv11.network

import java.io.Serializable

data class LoginResponse(
    val MD5: String,
    val message: String,
    val roleList: List<Role>,
    val type: String
): Serializable

