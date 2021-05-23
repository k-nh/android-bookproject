package com.example.bookreviewsver.src.Main.MyPage.`interface`

import com.example.bookreviewsver.src.Main.MyPage.model.EditInfoBody
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.MyPageResponse
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.GET_LIKE_LIST
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.GET_MY_PAGE
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {


    @GET(GET_MY_PAGE)
    fun getMyPage(
        @Query("useridx") userIdx: Int
    ): Call<MyPageResponse>

    @PATCH(GET_MY_PAGE)
    fun editUserInfo(@Body params: EditInfoBody): Call<MyPageResponse>

    @GET(GET_LIKE_LIST)
    fun getLikeList(): Call <LikeListResponse>

}