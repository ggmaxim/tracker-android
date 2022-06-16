package com.example.myapplication3.network.service

import com.example.myapplication3.model.response.users.AllUsersResponse
import com.example.myapplication3.model.response.tests.AddTestResponse
import com.example.myapplication3.model.response.auth.LoginResponse
import com.example.myapplication3.model.response.auth.RegisterUserResponse
import com.example.myapplication3.model.response.location.AddLocationResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {

@FormUrlEncoded
@POST("register")
fun registerUser(
    @Field("email") email: String,
    @Field("password") password:String,
    @Field("cnp") cnp: String,
    @Field("role") role: String,
    @Field("full_name") full_name: String
): Call<RegisterUserResponse>


@FormUrlEncoded
@POST("login")
fun authenticateUser(
    @Field("email") email:String,
    @Field("password") password:String
): Call<LoginResponse>

@GET("users")
fun getAllUsers(
):Call<AllUsersResponse>

@GET("users")
fun getFilteredUsers(
    @Query("role") role: String,
):Call<AllUsersResponse>


@GET("positive")
fun getPositiveUsers(
):Call<AllUsersResponse>

@GET("contact")
fun getContactUsers(
):Call<AllUsersResponse>



@FormUrlEncoded
@POST("tests")
    fun addTest(
        @Field("cnp") cnp: String,
        @Field("result") result:String,
        @Field("type") type: String,
        @Field("date") date: String
): Call<AddTestResponse>

@FormUrlEncoded
@POST("location")
fun addLocation(
    @Field("id") id: String,
    @Field("latitude") result:Number,
    @Field("longitude") type: Number
): Call<AddLocationResponse>

}