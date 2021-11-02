package com.example.haditsku.activity

import com.google.gson.annotations.SerializedName

data class UserResponse(
        @SerializedName("id")
        var id:Int,
        @SerializedName("username")
        var username:String,
        @SerializedName("password")
        var password:String,
        @SerializedName("first_name")
        var first_name:String,
        @SerializedName("last_name")
        var last_name:String,
        @SerializedName("message")
        var message:String,
)
