package com.example.bookreviewsver.src.Main.Home.model

import com.google.gson.annotations.SerializedName

data class LikeBody (
    @SerializedName("bookID") val bookID : Int,
    @SerializedName("book_name") val bookName : String,
    @SerializedName("bookImgUrl") val bookImgUrl: String,
    @SerializedName("categoryID") val categoryId: Int
    )
