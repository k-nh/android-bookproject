package com.example.bookreviewsver.src.Main.Book.service

import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.Book.model.*
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.BookFragmentView
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
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

class BookRetrofitManager(val view: BookFragmentView) {

    fun postBookRead(id:String,isbn:String,bookId: Int, BookName:String,BookImgUrl:String, endDay:String) {
        val bookRetrofitInterface: BookRetrofitInterface? =
                MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                        BookRetrofitInterface::class.java
                )
        bookRetrofitInterface?.postRead(ReadBody(id,isbn,bookId, BookName, BookImgUrl,endDay))?.enqueue(object : Callback<ReadResponse?> {
            override fun onResponse(
                    call: Call<ReadResponse?>,
                    response: Response<ReadResponse?>
            ) {
                Log.d("좋아요","read")

            }
            override fun onFailure(call: Call<ReadResponse?>, t: Throwable) {
                Log.d("좋아요","read실패")

            }
        })
    }

    fun postBookReading(id:String,isbn:String,bookId: Int, BookName:String,BookImgUrl:String, startDay: String) {
        val bookRetrofitInterface: BookRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                BookRetrofitInterface::class.java
            )
        bookRetrofitInterface?.postReading(ReadingBody(id,isbn,bookId,BookName,BookImgUrl,startDay))?.enqueue(object : Callback<ReadingResponse?> {
            override fun onResponse(
                call: Call<ReadingResponse?>,
                response: Response<ReadingResponse?>
            ) {
                Log.d("좋아요","reading")

            }
            override fun onFailure(call: Call<ReadingResponse?>, t: Throwable) {
                Log.d("좋아요","reading 실패")

            }
        })
    }



    fun getCheckList() {
        val bookRetrofitInterface: BookRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                BookRetrofitInterface::class.java
            )
        bookRetrofitInterface?.getCheckList()?.enqueue(object : retrofit2.Callback<CheckListResponse?> {
            override fun onResponse(call: Call<CheckListResponse?>, response: Response<CheckListResponse?>) {
                val checkListResponse: CheckListResponse? = response.body()
                view.getCheckListSuccess(checkListResponse as CheckListResponse)
            }

            override fun onFailure(call: Call<CheckListResponse?>, t: Throwable) {
                view.getCheckListFailure(t.message ?: "통신오류")
            }

        })
    }

    fun postBookLike(isbn:String,bookId: Int, BookName:String,BookImgUrl:String,CategoryId:Int, Promise:String) {
        val bookRetrofitInterface: BookRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                BookRetrofitInterface::class.java
            )
        bookRetrofitInterface?.postLike(LikeBody(isbn,bookId,BookName,BookImgUrl,CategoryId,Promise))?.enqueue(object : Callback<LikeResponse?> {
            override fun onResponse(call: Call<LikeResponse?>, response: Response<LikeResponse?>) {
                Log.d("좋아요 성공","좋아요 성공")

            }

            override fun onFailure(call: Call<LikeResponse?>, t: Throwable) {
                Log.d("좋아요 실패","좋아요 실패")
            }
        })
    }



}