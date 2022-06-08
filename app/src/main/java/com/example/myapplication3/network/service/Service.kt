package com.example.myapplication3.network.service

import com.example.myapplication3.model.response.users.AddUserResponse
import com.example.myapplication3.model.response.users.AllUsersResponse
import com.example.myapplication3.model.response.users.DeleteUserResponse
import com.example.myapplication3.model.response.auth.LoginResponse
import com.example.myapplication3.model.response.auth.RegisterUserResponse
import com.example.myapplication3.model.response.users.UpdateUserResponse
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



@FormUrlEncoded
@POST("users/add.php")
fun addUser(
    @Field("username") username: String,
    @Field("password") password:String,
    @Field("expiry") expiry: String
): Call<AddUserResponse>


@FormUrlEncoded
@POST("users/update.php")
fun updateUser(
    @Field("id") id: String,
    @Field("username") username: String,
    @Field("password") password:String,
    @Field("deviceId") deviceId: String,
    @Field("expiry") expiry: String,
): Call<UpdateUserResponse>


@GET("users")
fun getAllUsers():Call<AllUsersResponse>


@FormUrlEncoded
@POST("users/delete.php")
    fun deleteUser(
        @Field("id") id: String,
): Call<DeleteUserResponse>

}