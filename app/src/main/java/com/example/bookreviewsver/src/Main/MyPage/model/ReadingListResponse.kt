package com.example.bookreviewsver.src.Main.MyPage.model


data class ReadingListResponse(
    val isSuccess: Boolean,
    val result: List<ReadingListResult>
)