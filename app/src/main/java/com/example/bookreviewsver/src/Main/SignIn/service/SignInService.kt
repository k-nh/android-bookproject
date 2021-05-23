package com.example.bookreviewsver.src.Main.SignIn.service

import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.BASE_URL
import com.example.bookreviewsver.src.Main.SignIn.`interface`.SignInActivityView
import com.example.bookreviewsver.src.Main.SignIn.model.SignInAutoResponse
import com.example.bookreviewsver.src.Main.SignIn.model.SignInBody
import com.example.bookreviewsver.src.Main.SignIn.model.SignInResponse
import com.example.bookreviewsver.src.home.`interface`.SignInRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInService(val view: SignInActivityView) {

    fun PostSignIn(id: String, pw: String) {
        val signInInterface: SignInRetrofitInterface? = MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(SignInRetrofitInterface::class.java)
        signInInterface?.postSignIn(SignInBody(id, pw))?.enqueue(object : Callback<SignInResponse?> {
            override fun onResponse(call: Call<SignInResponse?>, response: Response<SignInResponse?>) {
                Log.d("PostSignIn", "성공")
                if (response.body().toString() == null) {
                    view.validateFailure(null)
                    return
                }
                view.validateSuccess(response.body() as SignInResponse)
            }

            override fun onFailure(call: Call<SignInResponse?>, t: Throwable) {
                view.validateFailure(t.message ?: "통신오류")
            }
        })
    }

    fun getSignInAuto() {
        val signInInterface: SignInRetrofitInterface? = MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(SignInRetrofitInterface::class.java)
        signInInterface?.getSignInAuto()?.enqueue(object : retrofit2.Callback<SignInAutoResponse?> {
            override fun onResponse(call: Call<SignInAutoResponse?>, response: Response<SignInAutoResponse?>) {
                Log.d("자동로그인", response.body().toString())
                if (response.body().toString() == null) {
                    view.SignInAutoFailure(null)
                    return
                }
                view.SignInAutoSuccess(response.body() as SignInAutoResponse)

            }

            override fun onFailure(call: Call<SignInAutoResponse?>, t: Throwable) {
                view.SignInAutoFailure(t.message ?: "통신오류")
            }
        })
    }


}