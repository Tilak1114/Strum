package com.strum.app.models

import com.google.gson.annotations.SerializedName

data class ProjectDetailModel(
    @SerializedName("description")
    var description: String,
    @SerializedName("users")
    var teamList: List<User>,
    @SerializedName("tasks")
    var taskList: List<WorkTask>
    )