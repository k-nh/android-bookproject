package com.example.bookreviewsver.src.Main.MyPage.model


import com.google.gson.annotations.SerializedName

data class ReadingListResult(
    val bookImgUrl : String,
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    val author: String,
    val publisher: String,
    val description: String,
    val pubdate: String,
    val link: String,
    @SerializedName("reading_start_date")
    val startDate: String,
    val isbn: String
)