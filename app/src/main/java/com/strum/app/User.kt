package com.strum.app

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id")
    var userid: Int,
    @SerializedName("name")
    var username: String,

    var url: String)
