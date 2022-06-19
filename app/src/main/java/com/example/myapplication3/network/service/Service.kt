package com.example.myapplication3.network.service

import com.example.myapplication3.model.response.users.AllUsersResponse
import com.example.myapplication3.model.response.tests.AddTestResponse
import com.example.myapplication3.model.response.auth.LoginResponse
import com.example.myapplication3.model.response.auth.RegisterUserResponse
import com.example.myapplication3.model.response.location.AddLocationResponse
import com.example.myapplication3.model.response.users.DeleteUserResponse
import com.example.myapplication3.model.response.users.UpdateUserResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {

@FormUrlEncoded
@POST("users")
fun registerUser(
    @Field("email") email: String,
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
    @Query("positive") positive: Boolean,
    @Query("contacts") contacts: Boolean,
):Call<AllUsersResponse>

@GET("users")
fun getRoleUsers(
    @Query("role") role: String,
):Call<AllUsersResponse>


@FormUrlEncoded
@PATCH("users/{user_id}")
fun updateUser(
    @Path("user_id") user_id: String,
    @Field("email") email: String,
    @Field("cnp") cnp: String,
    @Field("full_name") full_name: String,
    @Field("role") role: String,
):Call<UpdateUserResponse>


@FormUrlEncoded
@PATCH("users/{user_id}")
fun updatePassword(
    @Path("user_id") user_id: String,
    @Field("password") password: String
):Call<UpdateUserResponse>


@DELETE("users/{user_id}")
fun deleteUser(
    @Path("user_id") user_id: String
):Call<DeleteUserResponse>

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