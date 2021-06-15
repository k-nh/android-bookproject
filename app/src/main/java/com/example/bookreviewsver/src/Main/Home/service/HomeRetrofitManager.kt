package com.example.bookreviewsver.src.Main.Home.service

import android.text.Html
import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI.BOOK_URL
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants.TAG
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver.src.Main.Home.model.BestSellerData
import com.example.bookreviewsver.src.Main.Home.model.LikeBody
import com.example.bookreviewsver.src.Main.Home.model.LikeResponse
import com.example.bookreviewsver.src.Main.Home.model.SearchData
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.home.`interface`.BookRetrofitInterface
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
    private val homeRetrofitInterface : HomeRetrofitInterface?
    = MyApplication.RetrofitClient2.getCleint2(BOOK_URL)?.create(HomeRetrofitInterface::class.java)

    fun getBestSeller(key: String, categoryId: Int , output: String, completion: (RESPONSE_STATE, ArrayList<BestSellerData>?) -> Unit){
        val call = homeRetrofitInterface?.getBestSeller(key = BOOKAPI.CLIENT_ID, categoryId= categoryId, output = output).let{
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
                            var stCategoryId: String = "100"
                            val parasedBestSellerDataArray = ArrayList<BestSellerData>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("item")

                            Log.d(TAG,"retrofit manager - onResponse() called")
                            results.forEach { resultItem ->
                                // json obj 안에 또 json obj 이면
                                val Item : JsonObject = resultItem.asJsonObject
                                val ItemId : Int = Item.get("itemId").asInt
                                if (Item.has("categoryId")){
                                    stCategoryId = Item.get("categoryId").asString
                                }
                                val categoryId = stCategoryId!!.toInt()
                                val rank : Int = Item.get("rank").asInt
                                val coverImgUrl : String = Item.get("coverLargeUrl").asString
                                val title : String = Item.get("title").asString
                                val author : String = Item.get("author").asString
                                val publisher : String = Item.get("publisher").asString
                                val descriptionBefore : String = Item.get("description").asString
                                val description:String = stripHtml(descriptionBefore)
                                val pubDate : String = Item.get("pubDate").asString
                                val link : String = Item.get("link").asString
                                val isbn : String = Item.get("isbn").asString
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
                                                                    categoryId = categoryId,
                                                                    isbn = isbn,
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


    fun stripHtml(html:String): String {
        var afterHtml :String = Html.fromHtml(html).toString()
        return afterHtml
    }


    fun getBookDetail(key: String, query: String, queryType: String, output: String, completion: (RESPONSE_STATE, ArrayList<SearchData>?) -> Unit){
        val call = homeRetrofitInterface?.getBookDetail(key = BOOKAPI.CLIENT_ID, query = query, queryType = queryType, output = output).let{
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
                            var stCategoryId = "100"
                            val parasedSearchDataArray = ArrayList<SearchData>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("item")

                            Log.d(TAG,"retrofit manager searchBook - onResponse() called")
                            results.forEach { resultItem ->
                                val Item : JsonObject = resultItem.asJsonObject
                                val ItemId : Int = Item.get("itemId").asInt
                                if (Item.has("categoryId")){
                                    stCategoryId = Item.get("categoryId").asString
                                }
                                val categoryId = stCategoryId!!.toInt()
                                val coverImgUrl : String = Item.get("coverLargeUrl").asString
                                val title : String = Item.get("title").asString
                                val author : String = Item.get("author").asString
                                val publisher : String = Item.get("publisher").asString
                                val descriptionBefore : String = Item.get("description").asString
                                val description:String = stripHtml(descriptionBefore)
                                val pubDate : String = Item.get("pubDate").asString
                                val link : String = Item.get("link").asString
                                val isbn : String = Item.get("isbn").asString
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
                                                                    categoryId = categoryId,
                                                                    isbn = isbn,
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

    fun getSearchBook(key: String, query: String, output: String, completion: (RESPONSE_STATE, ArrayList<SearchData>?) -> Unit){
        val call = homeRetrofitInterface?.getSearchBook(key = BOOKAPI.CLIENT_ID, query = query, output = output).let{
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
                            var stCategoryId = "100"
                            var isbn = "0"
                            val parasedSearchDataArray = ArrayList<SearchData>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("item")

                            Log.d(TAG,"retrofit manager searchBook - onResponse() called")
                            results.forEach { resultItem ->
                                val Item : JsonObject = resultItem.asJsonObject
                                val ItemId : Int = Item.get("itemId").asInt
                                if (Item.has("categoryId")){
                                    stCategoryId = Item.get("categoryId").asString
                                }
                                val categoryId = stCategoryId!!.toInt()
                                val coverImgUrl : String = Item.get("coverLargeUrl").asString
                                val title : String = Item.get("title").asString
                                val author : String = Item.get("author").asString
                                val publisher : String = Item.get("publisher").asString
                                val descriptionBefore : String = Item.get("description").asString
                                val description:String = stripHtml(descriptionBefore)
                                val pubDate : String = Item.get("pubDate").asString
                                val link : String = Item.get("link").asString
                                if(Item.has("isbn")) {
                                    isbn = Item.get("isbn").asString
                                }
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
                                                                    categoryId = categoryId,
                                                                    isbn = isbn,
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