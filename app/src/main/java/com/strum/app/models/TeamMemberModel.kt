package com.strum.app.models

import com.google.gson.annotations.SerializedName

data class TeamMemberModel(
    @SerializedName("profileurl")
    var url: String,
    @SerializedName("userid")
    var userId: Int,
    @SerializedName("username")
    var userName: String)