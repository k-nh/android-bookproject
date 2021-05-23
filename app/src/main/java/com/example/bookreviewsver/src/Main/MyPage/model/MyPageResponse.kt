package com.example.bookreviewsver.src.Main.MyPage.model

import com.example.bookreviewsver.src.Main.MyPage.model.Result
import com.google.gson.annotations.SerializedName

data class MyPageResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("result") val result: Result
)