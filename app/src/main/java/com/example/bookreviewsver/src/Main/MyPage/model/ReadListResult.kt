package com.example.bookreviewsver.src.Main.MyPage.model


import com.google.gson.annotations.SerializedName

data class ReadListResult(
    val readId: Int,
    val bookImgUrl : String,
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    val author: String,
    val publisher: String,
    val description: String,
    val pubdate: String,
    val link: String,
    val regdate: String,
    @SerializedName("read_finish_date")
    val endDate: String,
    val isbn: String
)