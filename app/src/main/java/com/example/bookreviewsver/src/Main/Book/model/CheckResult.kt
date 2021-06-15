package com.example.bookreviewsver.src.Main.Book.model


import com.google.gson.annotations.SerializedName

data class CheckResult(
    val bookImgUrl : String,
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    @SerializedName("chk")
    val status: Int
)