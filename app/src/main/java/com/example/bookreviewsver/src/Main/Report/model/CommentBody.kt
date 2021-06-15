package com.example.bookreviewsver.src.Main.Report.model

import com.google.gson.annotations.SerializedName

data class CommentBody (
    @SerializedName("repostId") val repostId : Int,
    @SerializedName("comment") val comment : String,
    @SerializedName("id") val id: String,
    @SerializedName("profileImgUrl") val profileImgUrl : String

)
