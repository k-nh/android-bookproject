package com.example.bookreviewsver.src.Main.Report.model

import com.google.gson.annotations.SerializedName

data class PostReportBody (
        @SerializedName("title") val title : String,
        @SerializedName("bookID") val bookId : Int,
        @SerializedName("book_name") val book_name : String,
        @SerializedName("content") val content : String,
        @SerializedName("id") val id : String,
        @SerializedName("coverImgUrl") val coverImgUrl : String

        )
