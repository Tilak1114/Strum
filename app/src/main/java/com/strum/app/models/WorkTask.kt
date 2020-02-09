package com.strum.app.models

import com.google.gson.annotations.SerializedName

data class WorkTask(
    var taskname: String,
    var deadline: String,
    var taskid: Int,
    var priority: String,
    var userid: Int,
    var status: String)