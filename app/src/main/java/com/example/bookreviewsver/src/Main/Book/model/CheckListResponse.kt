package com.example.bookreviewsver.src.Main.Book.model


data class CheckListResponse(
    val isSuccess: Boolean,
    val result: List<CheckResult>
)