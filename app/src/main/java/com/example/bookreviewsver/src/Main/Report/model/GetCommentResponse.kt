package com.example.bookreviewsver.src.Main.Report.model


data class GetCommentResponse(
    val isSuccess: Boolean,
    val result: List<GetCommentResult>
)

data class GetCommentResult(
    val comment: String,
    val commentId: Int,
    val id: String,
    val regdate: String,
    val repostId: Int,
    val profileImgUrl: String
)