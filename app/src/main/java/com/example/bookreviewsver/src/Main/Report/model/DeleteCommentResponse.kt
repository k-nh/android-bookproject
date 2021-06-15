package com.example.bookreviewsver.src.Main.Report.model


data class DeleteCommentResponse(
    val isSuccess: Boolean,
    val result: DeleteResult
)

data class DeleteResult(
    val comment: Any,
    val commentId: Int,
    val id: Any,
    val regdate: Any,
    val repostId: Int)

data class DeleteReportResponse(
    val isSuccess: Boolean,
    val result: DeleteResult
)