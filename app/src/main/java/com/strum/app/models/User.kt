package com.strum.app.models

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id")
    var userid: Int,
    @SerializedName("username")
    var username: String,

    var url: String)
