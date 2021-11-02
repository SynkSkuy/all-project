package com.example.haditsku.activity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id")
    var id:Int,
    @SerializedName("chapter")
    var chapter:String,
    @SerializedName("naratter")
    var netter:String,
    @SerializedName("arabic")
    var arabic:String,
    @SerializedName("category")
    var category:String,
    @SerializedName("content")
    var content:String,
    @SerializedName("message")
    var message:String,
    @SerializedName("term")
    var term:String

)
