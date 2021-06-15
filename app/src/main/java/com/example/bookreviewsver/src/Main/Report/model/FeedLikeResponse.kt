package com.example.bookreviewsver.src.Main.Report.model

import com.google.gson.annotations.SerializedName

data class FeedLikeResponse (
    val isSuccess: Boolean,
    val result: FeedLikeResult

)

data class FeedLikeResult (
    @SerializedName("favorite_reviewId")
    val FeedId: Int,
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    val content: String,
    val id: String,
    val regdate: String,
    val repostId: Int,
    val title: String
    )

data class FeedLikeBody (
    @SerializedName("repostId") val repostId : Int,
    @SerializedName("title") val title : String,
    @SerializedName("bookID") val bookId : Int,
    @SerializedName("book_name") val book_name : String,
    @SerializedName("content") val content : String,
    @SerializedName("writer") val id : String,
    @SerializedName("coverImgUrl") val coverImgUrl : String,
    @SerializedName("profileImgUrl") val profileImgUrl : String,
    @SerializedName("regdate") val regdate : String

)
