//package com.example.bookreviewsver112.src.home.service
//
//import com.example.bookreviewsver112.R
//import com.example.bookreviewsver112.src.home.model.BestSellerResponse
//import io.reactivex.Single
//import okhttp3.Response
//
//
//class BookRemoteData(private val api: BookApi) {
//    fun getBestSeller(categoryId: Int): Single<Response<BestSellerResponse>> {
//        return api.getBestSeller(R.string.BEST_SELLER_API_KEY, categoryId, "json")
//    }
//}
//
//class BookApi {
//
//}
