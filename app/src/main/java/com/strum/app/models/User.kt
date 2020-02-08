package com.strum.app.models

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("userid")
    var userid: Int,
    @SerializedName("username")
    var username: String,
    @SerializedName("profileurl")
    var url: String)
