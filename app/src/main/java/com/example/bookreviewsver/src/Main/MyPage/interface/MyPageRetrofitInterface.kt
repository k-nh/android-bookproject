package com.example.bookreviewsver.src.Main.MyPage.`interface`

import com.example.bookreviewsver.src.Main.MyPage.model.*
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.GET_LIKE_LIST
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.GET_MY_PAGE
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {


    @GET(GET_MY_PAGE)
    fun getMyPage(): Call<MyPageResponse>

    @PATCH(GET_MY_PAGE)
    fun editUserInfo(@Body params: EditInfoBody): Call<EditMyPageResponse>

    @GET(GET_LIKE_LIST)
    fun getLikeList(): Call <LikeListResponse>


    @GET("user/readlist")
    fun getReadList(): Call<ReadListResponse>

    @GET("user/readinglist")
    fun getReadingList(): Call<ReadingListResponse>

}