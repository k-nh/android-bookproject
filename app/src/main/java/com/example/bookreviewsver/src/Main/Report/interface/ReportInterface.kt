package com.example.bookreviewsver.src.home.`interface`

import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.Main.Report.model.*
import retrofit2.Call
import retrofit2.http.*

interface ReportInterface {
    @GET(API.GET_BOARD_LIST)
    fun getBoardList(): Call<ReportResponse>

    @POST("book")
    fun postReport(@Body params: PostReportBody): Call<PostReportResponse>

    @GET("book/{repost_id}")
    fun getReport(
            @Path("repost_id") reportId: Int
    ): Call<GetReportResponse>

    @PATCH("book/{repost_id}")
    fun editReport(
        @Body params: EditReportBody,
        @Path("repost_id") reportId: Int
    ): Call<EditReportResponse>


    @PATCH("comment/{commentid}")
    fun editComment(
        @Body params: EditCommentBody,
        @Path("commentid") commentid: Int
    ): Call<EditCommentResponse>

    @GET("commentList/{repostId}")
    fun getCommentList(
        @Path("repostId") repostId: Int
    ): Call<GetCommentResponse>

    @DELETE("comment/{commentid}")
    fun deleteComment(@Path("commentid") commentid: Int): Call<DeleteCommentResponse>


    @DELETE("book/{repostId}")
    fun deleteReport(@Path("repostId") repostId: Int): Call<DeleteReportResponse>


    @POST("comment")
    fun postComment(@Body params: CommentBody): Call<PostCommentResponse>


    @POST("favoriteReview")
    fun postLike(@Body params: FeedLikeBody): Call<FeedLikeResponse>

    @GET("favoriteReviewList")
    fun getFavoriteFeedList(): Call<LikeFeedListResponse>

    @GET("favoriteReviewRepostIdList")
    fun getFavoriteFeedIdList(): Call<LikeFeedIdListResponse>

}