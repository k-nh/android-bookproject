package com.example.bookreviewsver.src.Main.Report.model

import com.google.gson.annotations.SerializedName


data class EditReportResponse(
    val isSuccess: Boolean,
    val result: Result
)

data class Result(
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    val content: String,
    val id: String,
    val regdate: String,
    val repostId: Int,
    val isbn:String,
    var like: Boolean = false,
    val title: String,
    val coverImgUrl: String,
    val viewcnt: Int,
    val profileImgUrl: String,
    val secret: Boolean

)