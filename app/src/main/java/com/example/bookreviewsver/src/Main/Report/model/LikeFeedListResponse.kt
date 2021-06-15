package com.example.bookreviewsver.src.Main.Report.model

import com.google.gson.annotations.SerializedName


data class LikeFeedListResponse(
    val isSuccess: Boolean,
    val result: List<LikeFeedResult>
)

data class LikeFeedResult(
    val bookID: Int,
    @SerializedName("book_name")
    val bookName: String,
    val content: String,
    val writer: String,
    val regdate: String,
    val repostId: Int,
    var like: Boolean = false,
    val title: String,
    val coverImgUrl: String,
    val viewcnt: Int,
    val profileImgUrl: String,
    val secret: Boolean

)

data class LikeFeedIdListResponse(
    val result: List<IdList>
)

data class IdList (
    val repostId: Int
    )
