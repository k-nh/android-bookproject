package com.example.bookreviewsver.src.Main.Report.model


data class EditCommentResult(
    val comment: String,
    val commentId: Int,
    val id: String,
    val regdate: String,
    val repostId: Int
)