//package com.example.bookreviewsver112.src.home.`interface`
//
//import com.example.bookreviewsver112.src.home.model.BestSellerResponse
//import io.reactivex.Single
//import retrofit2.Call
//import retrofit2.Response
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface HomeInterface {
//
//    @GET("bestSeller.api")
//    Call<BestSellerResponse> getBestSeller(
//    @Query("key") String key,
//    @Query("categoryId") Int categoryId,
//    @Query("output") String output
//    )
//}