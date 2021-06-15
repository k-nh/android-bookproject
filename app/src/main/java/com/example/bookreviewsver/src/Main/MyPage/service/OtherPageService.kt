package com.example.bookreviewsver.src.Main.SignUp.service

import OtherPageActivityView
import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.MyPage.`interface`.OtherPageInterface
import com.example.bookreviewsver.src.Main.MyPage.model.*
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.BASE_URL
import retrofit2.Call
import retrofit2.Response

class OtherPageService(val view: OtherPageActivityView) {

    fun getOtherUserInfo(id: String) {
        val otherPageInterface: OtherPageInterface? =
            MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(
                OtherPageInterface::class.java
            )
        otherPageInterface?.getOtherUserInfo(id)
            ?.enqueue(object : retrofit2.Callback<MyPageResponse?> {
                override fun onResponse(
                    call: Call<MyPageResponse?>,
                    response: Response<MyPageResponse?>
                ) {
                    val myPageResponse: MyPageResponse? = response.body()
                    if (myPageResponse == null) {
                        Log.d("실패", "getOtherUserInfo null")
                        return
                    }
                    view.otherPageSuccess(response.body() as MyPageResponse)
                }

                override fun onFailure(call: Call<MyPageResponse?>, t: Throwable) {
                    Log.d("실패", "getUserInfo")
                }
            })
    }

    fun getOtherLikeList(id: String) {
        val otherPageInterface: OtherPageInterface? =
            MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(
                OtherPageInterface::class.java
            )
        otherPageInterface?.getOtherLikeList(id)?.enqueue(object : retrofit2.Callback<LikeListResponse?> {
            override fun onResponse(
                call: Call<LikeListResponse?>,
                response: Response<LikeListResponse?>
            ) {
                val likeListResponse: LikeListResponse? = response.body()
                view.otherLikeListSuccess(likeListResponse as LikeListResponse)
            }

            override fun onFailure(call: Call<LikeListResponse?>, t: Throwable) {
                Log.d("실패", "getlike")
            }

        })
    }

    fun getReadList(id: String) {
        val otherPageInterface: OtherPageInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                OtherPageInterface::class.java
            )
        otherPageInterface?.getOtherReadList(id)
            ?.enqueue(object : retrofit2.Callback<ReadListResponse?> {
                override fun onResponse(
                    call: Call<ReadListResponse?>,
                    response: Response<ReadListResponse?>
                ) {
                    val readListResponse: ReadListResponse? = response.body()
                    view.otherReadListSuccess(readListResponse as ReadListResponse)
                }

                override fun onFailure(call: Call<ReadListResponse?>, t: Throwable) {
                    Log.d("실패", "getread")
                }

            })
    }

    fun getReadingList(id: String) {
        val otherPageInterface: OtherPageInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                OtherPageInterface::class.java
            )
        otherPageInterface?.getOtherReadingList(id)
            ?.enqueue(object : retrofit2.Callback<ReadingListResponse?> {
                override fun onResponse(
                    call: Call<ReadingListResponse?>,
                    response: Response<ReadingListResponse?>
                ) {
                    val readingListResponse: ReadingListResponse? = response.body()
                    view.otherReadingListSuccess(readingListResponse as ReadingListResponse)
                }

                override fun onFailure(call: Call<ReadingListResponse?>, t: Throwable) {
                    Log.d("실패", "getreading")
                }

            })
    }
}

