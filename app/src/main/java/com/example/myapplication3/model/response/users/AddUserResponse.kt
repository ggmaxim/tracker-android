package com.example.myapplication3.model.response.users

data class AddUserResponse (
    val isSuccess: Int,
    val message: String,
    val username: String
)