package com.example.bookreviewsver112.src.home.`interface`

import com.example.bookreviewsver112.src.Main.home.`interface`.API
import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRetrofitInterface {

    @GET(API.GET_BEST_SELLER)
    fun getBestSeller(
        @Query("key") key : String,
        @Query("categoryId") categoryId : Int,
        @Query("output") output : String) : Call<JsonElement>

    @GET(API.GET_SEARCH_BOOK)
    fun getSearchBook(
            @Query("key") key : String,
            @Query("query") query : String,
            @Query("output") output : String) : Call<JsonElement>


}