package com.strum.app.services

import com.strum.app.models.*
import okhttp3.internal.concurrent.Task
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

    @Headers("Content-Type: application/json")
    @POST("/api/addprojecttasks")
    fun newTask(@Body body: String?): Call<WorkTask>

    @Headers("Content-Type: application/json")
    @PUT("/api/updatestatus")
    fun changeStatus(@Body body: String?): Call<StatusModel>
}