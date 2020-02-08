package com.strum.app.models

import com.google.gson.annotations.SerializedName

data class ProjectModel(
    @SerializedName("projectid")
    var projId: Int,

    var projSName: String?,

    @SerializedName("projectname")
    var projFName: String,


    @SerializedName("adminname")
    var projectAdmin: String)