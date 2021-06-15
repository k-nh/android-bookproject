package com.example.bookreviewsver.src.Main.Report.model


data class EditCommentResponse(
    val isSuccess: Boolean,
    val result: EditCommentResult
)

data class EditCommentResult(
    val comment: String,
    val commentId: Int,
    val id: String,
    val regdate: String,
    val repostId: Int
)