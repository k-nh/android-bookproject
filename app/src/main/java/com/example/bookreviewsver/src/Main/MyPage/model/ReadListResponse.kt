package com.example.bookreviewsver.src.Main.MyPage.model


data class ReadListResponse(
    val isSuccess: Boolean,
    val result: List<ReadListResult>
)