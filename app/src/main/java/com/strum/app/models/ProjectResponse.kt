package com.strum.app.models

import com.google.gson.annotations.SerializedName

data class ProjectResponse(
    @SerializedName("projects")
    var projects : List<ProjectModel>)