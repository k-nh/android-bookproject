package com.example.bookreviewsver.src.Main.Report.model


import com.google.gson.annotations.SerializedName

data class PostResult(
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    val content: String,
    val id: String,
    val regdate: String,
    val repostId: Int,
    val title: String,
    val viewcnt: Int
)