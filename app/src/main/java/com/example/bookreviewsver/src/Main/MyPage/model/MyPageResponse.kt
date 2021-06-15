package com.example.bookreviewsver.src.Main.MyPage.model

import com.example.bookreviewsver.src.Main.MyPage.model.Result
import com.google.gson.annotations.SerializedName

data class MyPageResponse(
        @SerializedName("reviewCnt") val reviewCnt: Int,
        @SerializedName("readCnt") val readCnt: Int,
        @SerializedName("readingCnt") val readingCnt: Int,
        @SerializedName("favoriteCnt") val favoriteCnt: Int,
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("grade") val grade: String,
        @SerializedName("result") val result: Result
)