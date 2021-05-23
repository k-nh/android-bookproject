package com.example.bookreviewsver.src.Main.SignIn.model

import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("jwt") val jwt: String,
        @SerializedName("useridx") val useridx: Int,
        @SerializedName("id") val userId: String
)