package com.example.myapplication3.model.response.auth

data class LoginResponse(
    val isSuccess: Int,
    val message: String,
    val email: String
)