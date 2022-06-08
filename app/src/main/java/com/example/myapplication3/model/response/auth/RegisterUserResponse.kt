package com.example.myapplication3.model.response.auth

data class RegisterUserResponse(
   val isSuccess: Int,
   val message: String,
   val email: String
)
