package com.example.bookreviewsver.src.Main.Report.model

data class PostCommentResponse (
    val isSuccess: Boolean,
    val result: CommentResult

)

data class CommentResult (
    val commentId: Int,
    val repostId: Int,
    val comment: String,
    val id: String,
    val regdate: String,
    val profileImgUrl: String
)