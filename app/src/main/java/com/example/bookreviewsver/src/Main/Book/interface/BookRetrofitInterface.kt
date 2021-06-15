package com.example.bookreviewsver.src.home.`interface`

import com.example.bookreviewsver.src.Main.Book.model.*
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.model.LikeBody
import com.example.bookreviewsver.src.Main.Home.model.LikeResponse
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookRetrofitInterface {

    @POST("user/read")
    fun postRead(@Body params: ReadBody): Call<ReadResponse>

    @POST("user/reading")
    fun postReading(@Body params: ReadingBody): Call<ReadingResponse>

    @POST("user/like")
    fun postLike(@Body params: LikeBody): Call<LikeResponse>

    @GET("user/checklist")
    fun getCheckList(): Call<CheckListResponse>

}