package com.example.bookreviewsver.src.Main.Home.model


import com.google.gson.annotations.SerializedName

data class Result(
    val bookID: Int,
    val bookImgUrl: String,
    @SerializedName("book_name")
    val bookName: String,
    val favoriteId: Int,
    val id: String,
    val regdate: String,
    val categoryID: Int
)