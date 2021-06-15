package com.example.bookreviewsver.src.Main.Book.model


import com.google.gson.annotations.SerializedName

data class ReadResult(
    val readId: Int,
    val bookID: Int,
    val bookImgUrl: String,
    @SerializedName("book_name")
    val bookName: String,
    val id: String,
    val regdate: String
    )