package com.example.bookreviewsver.src.home.`interface`

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

interface HomeRetrofitInterface {

    @GET(BOOKAPI.GET_BEST_SELLER)
    fun getBestSeller(
            @Query("key") key: String,
            @Query("categoryId") categoryId: Int,
            @Query("output") output: String) : Call<JsonElement>

    @GET(BOOKAPI.GET_SEARCH_BOOK)
    fun getSearchBook(
            @Query("key") key: String,
            @Query("query") query: String,
            @Query("output") output: String) : Call<JsonElement>

    @GET(BOOKAPI.GET_SEARCH_BOOK)
    fun getBookDetail(
        @Query("key") key: String,
        @Query("query") query: String,
        @Query("queryType") queryType: String,
        @Query("output") output: String) : Call<JsonElement>


}