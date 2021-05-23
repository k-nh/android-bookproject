package com.example.bookreviewsver.src.Main.MyPage.model


import com.google.gson.annotations.SerializedName

data class LikeResult(
    val bookImgUrl : String,
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    val favoriteId: Int,
    val categoryID: Int,
    val id: String,
    val regdate: String
)