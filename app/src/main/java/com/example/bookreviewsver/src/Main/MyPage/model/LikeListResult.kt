package com.example.bookreviewsver.src.Main.MyPage.model


import com.google.gson.annotations.SerializedName

data class LikeListResult(
    val bookImgUrl : String,
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    val author: String,
    val publisher: String,
    val description: String,
    val pubdate: String,
    val link: String,
    val favoriteId: Int,
    val categoryID: Int,
    val id: String,
    val regdate: String,
    val isbn: String,
    val promise: String
)