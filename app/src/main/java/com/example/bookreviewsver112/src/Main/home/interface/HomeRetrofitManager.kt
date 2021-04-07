package com.example.bookreviewsver112.src.Main.home.`interface`

import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver112.src.Main.home.`interface`.API.BASE_URL
import com.example.bookreviewsver112.src.Main.home.`interface`.Constants.TAG
import com.example.bookreviewsver112.src.Main.home.model.BestSellerData
import com.example.bookreviewsver112.src.home.`interface`.HomeRetrofitInterface
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class HomeRetrofitManager {
    companion object {
        val instance = HomeRetrofitManager()
    }

    //http 콜 생성 , HomeRetrofitInterface 가져옴
    private val HomeRetrofit : HomeRetrofitInterface? = MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(HomeRetrofitInterface::class.java)

    fun getBestSeller(key: String, categoryId: Int, output: String, completion: (RESPONSE_STATE, ArrayList<BestSellerData>?) -> Unit){
        val call = HomeRetrofit?.getBestSeller(key = API.CLIENT_ID, categoryId= categoryId, output = output).let{
            it
        }?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.d(TAG,"retrofitmanager - onResponse / response : ${response.body()}")

                when(response.code()){
                    200 -> {
                        response.body()?.let {
                            val parasedBestSellerDataArray = ArrayList<BestSellerData>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("item")

//                            val totalResults = body.get("totalResults").asInt
//                            val searchCategoryId = body.get("searchCategoryId").asInt
                            Log.d(TAG,"retrofit manager - onResponse() called")
                            results.forEach { resultItem ->
                                // json obj 안에 또 json obj 이면
                                //val userId = resultItemObject.get("user").asJsonObject.get("userID").asString
                                val Item : JsonObject = resultItem.asJsonObject
                                val ItemId : Int = Item.get("itemId").asInt
                                val rank : Int = Item.get("rank").asInt
                                val coverImgUrl : String = Item.get("coverLargeUrl").asString
                                val title : String = Item.get("title").asString
                                val author : String = Item.get("author").asString
                                val publisher : String = Item.get("publisher").asString
                                val description : String = Item.get("description").asString
                                val pubDate : String = Item.get("pubDate").asString
                                val parser = SimpleDateFormat("yyyymmdd")
                                val formatter = SimpleDateFormat("yyyy년 mm월 dd일")
                                val outputPubDate = formatter.format(parser.parse(pubDate))

                                val BestSellerItem = BestSellerData(
                                                                    itemId = ItemId,
                                                                    rank = rank,
                                                                    coverImgUrl = coverImgUrl,
                                                                    title = title,
                                                                    author = author,
                                                                    publisher = publisher,
                                                                    description = description,
                                                                    pubDate = outputPubDate, favorite = false)
                                parasedBestSellerDataArray.add(BestSellerItem)
}
                            completion(RESPONSE_STATE.OKAY, parasedBestSellerDataArray)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"retrofitmanager - onFailure / t: $t")
                completion(RESPONSE_STATE.FAIL, null)
            }

        })
    }

}