package com.example.bookreviewsver.src.Main.Home.model


import com.google.gson.annotations.SerializedName

data class LikeResponse(
    val isSuccess: Boolean,
    val result: List<Result>
)