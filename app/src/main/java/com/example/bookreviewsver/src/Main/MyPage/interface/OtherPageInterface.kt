package com.example.bookreviewsver.src.Main.MyPage.`interface`

import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.MyPageResponse
import com.example.bookreviewsver.src.Main.MyPage.model.ReadListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.ReadingListResponse
import com.example.bookreviewsver.src.Main.Report.model.EditReportBody
import com.example.bookreviewsver.src.Main.Report.model.EditReportResponse
import com.example.bookreviewsver.src.Main.Report.model.GetReportResponse
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface OtherPageInterface {

    @GET("userInfo")
    fun getOtherUserInfo(
        @Query("id") id: String
        ): Call<MyPageResponse>

    @GET("UserChkListFavorite")
    fun getOtherLikeList(
        @Query("id") id: String
        ): Call<LikeListResponse>

    @GET("UserChkListReading")
    fun getOtherReadingList(
        @Query("id") id: String
        ): Call<ReadingListResponse>

    @GET("UserChkListRead")
    fun getOtherReadList(
        @Query("id") id: String
        ): Call<ReadListResponse>


}