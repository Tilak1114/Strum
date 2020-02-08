package com.strum.app.services

import com.strum.app.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface BackendApi {

    @Headers("Content-Type: application/json")
    @POST("/api/users")
    fun newUser(@Body body: String?): Call<User?>?


    @GET("/api/getusers")
    fun getUserInfo(): Call<User>

    @GET("")
    fun getProjects(): Call<User>
}