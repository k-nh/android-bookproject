package com.example.bookreviewsver.src.Main.SignUp.service

import MyPageFragmentView
import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.MyPage.`interface`.MyPageRetrofitInterface
import com.example.bookreviewsver.src.Main.MyPage.model.EditInfoBody
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.MyPageResponse
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.BASE_URL
import retrofit2.Call
import retrofit2.Response

class MyPageService(val view: MyPageFragmentView) {

    fun getUserInfo(userIdx : Int) {
        val myPageInterface: MyPageRetrofitInterface? =
            MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(
                MyPageRetrofitInterface::class.java
            )
        myPageInterface?.getMyPage(userIdx)?.enqueue(object : retrofit2.Callback<MyPageResponse?> {
            override fun onResponse(call: Call<MyPageResponse?>, response: Response<MyPageResponse?>) {
                val myPageResponse: MyPageResponse? = response.body()
                if (myPageResponse == null) {
                    view.myPageFailure(null)
                    return
                }
                view.myPageSuccess(response.body() as MyPageResponse)
            }

            override fun onFailure(call: Call<MyPageResponse?>, t: Throwable) {
                view.myPageFailure(t.message ?: "통신오류")
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
                view.likeListFailure(t.message ?: "통신오류")
            }

        })
    }

    fun editUserInfo(uri: String, nickname: String){
        val myPageInterface: MyPageRetrofitInterface? =
        MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(
            MyPageRetrofitInterface::class.java
        )
        myPageInterface?.editUserInfo(EditInfoBody(uri, nickname))?.enqueue(object :
            retrofit2.Callback<MyPageResponse?> {
            override fun onResponse(
                call: Call<MyPageResponse?>,
                response: Response<MyPageResponse?>
            ) {
                Log.d("마이페이지 EditUserInfo", response.body().toString())
                view.myPageSuccess(response.body() as MyPageResponse)

            }

            override fun onFailure(call: Call<MyPageResponse?>, t: Throwable) {
                view.myPageFailure(t.message ?: "통신오류")
            }
        })
    }}

