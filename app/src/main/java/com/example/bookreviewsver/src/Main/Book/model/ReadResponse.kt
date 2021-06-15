package com.example.bookreviewsver.src.Main.Book.model


data class ReadResponse(
    val isSuccess: Boolean,
    val result: List<ReadResult>
)