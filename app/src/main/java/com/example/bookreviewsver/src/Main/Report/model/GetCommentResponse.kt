package com.example.bookreviewsver.src.Main.Report.model


data class GetCommentResponse(
    val isSuccess: Boolean,
    val result: List<GetCommentResult>
)