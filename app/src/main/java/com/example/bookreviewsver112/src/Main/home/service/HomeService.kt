//package com.example.bookreviewsver112.src.Main.home.service
//
//import com.example.application.config.MyApplication
//import com.example.bookreviewsver112.src.Main.home.`interface`.HomeFragmentView
//import com.example.bookreviewsver112.src.home.`interface`.HomeRetrofitInterface
//import com.example.bookreviewsver112.src.home.model.BestSellerResponse
//import retrofit2.Call
//import retrofit2.Response
//import javax.security.auth.callback.Callback
//
//
//class HomeService(val view: HomeFragmentView) {
//
//    fun tryGetBestSeller(key: String, categoryId: Int, output: String){
//        val homeRetrofitInterface = MyApplication.sRetrofit.create(HomeRetrofitInterface::class.java)
//        homeRetrofitInterface.getBestSeller(key, categoryId, output).enqueue(object :
//            retrofit2.Callback<BestSellerResponse> {
//            override fun onResponse(
//                call: Call<BestSellerResponse>,
//                response: Response<BestSellerResponse>
//            ) {
//                view.onGetBestSellerSuccess(response.body() as BestSellerResponse)
//
//            }
//
//            override fun onFailure(call: Call<BestSellerResponse>, t: Throwable) {
//                view.onGetBestSellerFailure(t.message ?: "통신오류")
//            }
//
//        })
//
//    }
//}