package com.example.bookreviewsver.src.Main.Book.model

import com.google.gson.annotations.SerializedName

data class ReadingBody (
    @SerializedName("id") val id : String,
    @SerializedName("isbn") val isbn : String,
    @SerializedName("bookID") val bookID : Int,
    @SerializedName("book_name") val bookName : String,
    @SerializedName("bookImgUrl") val bookImgUrl: String,
    @SerializedName("reading_start_date") val startDay: String
    )
