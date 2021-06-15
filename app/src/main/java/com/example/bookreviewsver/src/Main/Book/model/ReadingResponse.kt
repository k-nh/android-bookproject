package com.example.bookreviewsver.src.Main.Book.model


data class ReadingResponse(
    val isSuccess: Boolean,
    val result: List<ReadingResult>
)