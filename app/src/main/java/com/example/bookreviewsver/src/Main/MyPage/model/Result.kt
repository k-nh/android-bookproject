package com.example.bookreviewsver.src.Main.MyPage.model

import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("useridx") val useridx: Int,
        @SerializedName("id") val id: String,
        @SerializedName("pw") val pw: String,
        @SerializedName("nickname") val nickname: String,
        @SerializedName("email") val email: String,
        @SerializedName("userImgUrl") val userImgUrl: String

        )