package com.strum.app

import retrofit2.Call
import retrofit2.http.GET

interface BackendApi {
    @GET("/api/getusers")
    fun getUserInfo(): Call<User>
}