package com.strum.app.services

import com.strum.app.models.ProjectDetailModel
import com.strum.app.models.ProjectModel
import com.strum.app.models.ProjectResponse
import com.strum.app.models.User
import retrofit2.Call
import retrofit2.http.*

interface BackendApi {

    @Headers("Content-Type: application/json")
    @POST("/api/users")
    fun newUser(@Body body: String?): Call<User?>?


    @GET("/api/getusers")
    fun getUserInfo(): Call<User>

    @GET("/api/getprojects")
    fun getProjects(): Call<ProjectResponse>

    @GET("/api/{projectid}/getdetails")
    fun getProjectDetails(@Path("projectid") projectId: Int): Call<ProjectDetailModel>
}