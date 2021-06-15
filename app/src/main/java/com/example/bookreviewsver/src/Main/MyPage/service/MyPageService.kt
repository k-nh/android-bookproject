package com.example.bookreviewsver.src.Main.SignUp.service

import MyPageFragmentView
import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.MyPage.`interface`.MyPageRetrofitInterface
import com.example.bookreviewsver.src.Main.MyPage.model.*
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.BASE_URL
import retrofit2.Call
import retrofit2.Response

class MyPageService(val view: MyPageFragmentView) {

    fun getUserInfo() {
        val myPageInterface: MyPageRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(
                MyPageRetrofitInterface::class.java
            )
        myPageInterface?.getMyPage()?.enqueue(object : retrofit2.Callback<MyPageResponse?> {
            override fun onResponse(call: Call<MyPageResponse?>, response: Response<MyPageResponse?>) {
                val myPageResponse: MyPageResponse? = response.body()
                if (myPageResponse == null) {
                    Log.d("실패","getUserInfo null")
                    return
                }
                view.myPageSuccess(response.body() as MyPageResponse)
            }

            override fun onFailure(call: Call<MyPageResponse?>, t: Throwable) {
                Log.d("실패","getUserInfo")
            }
        })
    }

    fun getLikeList() {
        val myPageInterface: MyPageRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(
                MyPageRetrofitInterface::class.java
            )
        myPageInterface?.getLikeList()?.enqueue(object : retrofit2.Callback<LikeListResponse?> {
            override fun onResponse(call: Call<LikeListResponse?>, response: Response<LikeListResponse?>) {
                val likeListResponse: LikeListResponse? = response.body()
                view.likeListSuccess(likeListResponse as LikeListResponse)
            }

            override fun onFailure(call: Call<LikeListResponse?>, t: Throwable) {
                Log.d("실패","getlike")
            }

        })
    }

    fun getReadList() {
        val myPageRetrofitInterface: MyPageRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                MyPageRetrofitInterface::class.java
            )
        myPageRetrofitInterface?.getReadList()?.enqueue(object : retrofit2.Callback<ReadListResponse?> {
            override fun onResponse(call: Call<ReadListResponse?>, response: Response<ReadListResponse?>) {
                val readListResponse: ReadListResponse? = response.body()
                view.getReadListSuccess(readListResponse as ReadListResponse)
            }
            override fun onFailure(call: Call<ReadListResponse?>, t: Throwable) {
                Log.d("실패","getread")
            }

        })
    }

    fun getReadingList() {
        val myPageRetrofitInterface: MyPageRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(
                MyPageRetrofitInterface::class.java
            )
        myPageRetrofitInterface?.getReadingList()?.enqueue(object : retrofit2.Callback<ReadingListResponse?> {
            override fun onResponse(call: Call<ReadingListResponse?>, response: Response<ReadingListResponse?>) {
                val readingListResponse: ReadingListResponse? = response.body()
                view.getReadingListSuccess(readingListResponse as ReadingListResponse)
            }

            override fun onFailure(call: Call<ReadingListResponse?>, t: Throwable) {
                Log.d("실패","getreading")
            }

        })
    }

    fun editUserInfo(userImgUrl: String){
        val myPageInterface: MyPageRetrofitInterface? =
        MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(
            MyPageRetrofitInterface::class.java
        )
        myPageInterface?.editUserInfo(EditInfoBody(userImgUrl))?.enqueue(object :
            retrofit2.Callback<EditMyPageResponse?> {
            override fun onResponse(
                call: Call<EditMyPageResponse?>,
                response: Response<EditMyPageResponse?>
            ) {
                Log.d("마이페이지 EditUserInfo", response.body().toString())
//                view.editMyPageSuccess(response.body() as EditMyPageResponse)

            }

            override fun onFailure(call: Call<EditMyPageResponse?>, t: Throwable) {
                Log.d("마이페이지 EditUserInfo","fail")
            }
        })
    }}

