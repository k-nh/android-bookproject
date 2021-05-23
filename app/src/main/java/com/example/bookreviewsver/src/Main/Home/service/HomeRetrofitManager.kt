package com.example.bookreviewsver.src.Main.Home.service

import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI.BOOK_URL
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants.TAG
import com.example.bookreviewsver.src.Main.Home.`interface`.HomeFragmentView
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver.src.Main.Home.model.BestSellerData
import com.example.bookreviewsver.src.Main.Home.model.LikeBody
import com.example.bookreviewsver.src.Main.Home.model.LikeResponse
import com.example.bookreviewsver.src.Main.Home.model.SearchData
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.home.`interface`.HomeRetrofitInterface
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class HomeRetrofitManager() {
    companion object {
        val instance = HomeRetrofitManager()
    }

    //http 콜 생성 , HomeRetrofitInterface 가져옴
    private val HomeRetrofit : HomeRetrofitInterface?
    = MyApplication.RetrofitClient2.getCleint2(BOOK_URL)?.create(HomeRetrofitInterface::class.java)
    val bookIdList = arrayListOf<Int>()

    fun getBestSeller(key: String, categoryId: Int,inputEncoding: String ,output: String, completion: (RESPONSE_STATE, ArrayList<BestSellerData>?) -> Unit){
        val call = HomeRetrofit?.getBestSeller(key = BOOKAPI.CLIENT_ID, categoryId= categoryId, inputEncoding = inputEncoding   ,output = output).let{
            it
        }?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.d(TAG,"retrofitmanager - onResponse / response")

                when(response.code()){
                    200 -> {
                        response.body()?.let {
                            val parasedBestSellerDataArray = ArrayList<BestSellerData>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("item")

                            Log.d(TAG,"retrofit manager - onResponse() called")
                            results.forEach { resultItem ->
                                // json obj 안에 또 json obj 이면
                                //val userId = resultItemObject.get("user").asJsonObject.get("userID").asString
                                val Item : JsonObject = resultItem.asJsonObject
                                val ItemId : Int = Item.get("itemId").asInt
                                val categoryId : Int = Item.get("categoryId").asInt
                                val rank : Int = Item.get("rank").asInt
                                val coverImgUrl : String = Item.get("coverLargeUrl").asString
                                val title : String = Item.get("title").asString
                                val author : String = Item.get("author").asString
                                val publisher : String = Item.get("publisher").asString
                                val description : String = Item.get("description").asString
                                val pubDate : String = Item.get("pubDate").asString
                                val link : String = Item.get("link").asString
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
                                                                    pubDate = outputPubDate,
                                                                    favorite = favoriteStatus(ItemId) ?: false,
                                                                    categoryId = categoryId,
                                                                    link = link)
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

    fun getBookLikeList() {
        val homeRetrofitInterface: HomeRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                HomeRetrofitInterface::class.java
            )
        homeRetrofitInterface?.getLikeList()?.enqueue(object : retrofit2.Callback<LikeListResponse?> {
            override fun onResponse(call: Call<LikeListResponse?>, response: Response<LikeListResponse?>) {
                val likeListResponse: LikeListResponse? = response.body()
                if (likeListResponse != null) {
                    if(likeListResponse.result != null){
                        for (i in likeListResponse.result){
                            if (favoriteStatus(i.bookID) == false)
                                bookIdList.add(i.bookID)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LikeListResponse?>, t: Throwable) {
            }
        })
    }

    private fun favoriteStatus(ItemId: Int): Boolean? {
        return bookIdList.contains(ItemId)
    }

    fun postBookLike(BookId:Int, BookName:String, BookImgUrl:String,CategoryId:Int) {
        val homeRetrofitInterface: HomeRetrofitInterface? =
                MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                        HomeRetrofitInterface::class.java
                )
        homeRetrofitInterface?.postLike(LikeBody(BookId, BookName,BookImgUrl,CategoryId))?.enqueue(object : Callback<LikeResponse?> {
            override fun onResponse(
                    call: Call<LikeResponse?>,
                    response: Response<LikeResponse?>
            ) {
                if(favoriteStatus(BookId) == true){
                    bookIdList.remove(BookId)
                }
            }

            override fun onFailure(call: Call<LikeResponse?>, t: Throwable) {
            }
        })
    }





    fun getSearchBook(key: String, query: String, output: String, completion: (RESPONSE_STATE, ArrayList<SearchData>?) -> Unit){
        val call = HomeRetrofit?.getSearchBook(key = BOOKAPI.CLIENT_ID, query = query, output = output).let{
            it
        }?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.d(TAG,"retrofitmanager searchBook - onResponse / response :")

                when(response.code()){
                    200 -> {
                        response.body()?.let {
                            val parasedSearchDataArray = ArrayList<SearchData>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("item")

                            Log.d(TAG,"retrofit manager searchBook - onResponse() called")
                            results.forEach { resultItem ->
                                val Item : JsonObject = resultItem.asJsonObject
                                val ItemId : Int = Item.get("itemId").asInt
                                val coverImgUrl : String = Item.get("coverLargeUrl").asString
                                val title : String = Item.get("title").asString
                                val author : String = Item.get("author").asString
                                val publisher : String = Item.get("publisher").asString
                                val description : String = Item.get("description").asString
                                val pubDate : String = Item.get("pubDate").asString
                                val link : String = Item.get("link").asString
                                val parser = SimpleDateFormat("yyyymmdd")
                                val formatter = SimpleDateFormat("yyyy년 mm월 dd일")
                                val outputPubDate = formatter.format(parser.parse(pubDate))

                                val SearchItem = SearchData(
                                                                    itemId = ItemId,
                                                                    coverImgUrl = coverImgUrl,
                                                                    title = title,
                                                                    author = author,
                                                                    publisher = publisher,
                                                                    description = description,
                                                                    pubDate = outputPubDate,
                                                                    favorite = favoriteStatus(ItemId) ?: false,
                                                                    link = link
                                )
                                parasedSearchDataArray.add(SearchItem)
}
                            completion(RESPONSE_STATE.OKAY, parasedSearchDataArray)
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