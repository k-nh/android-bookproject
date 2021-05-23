package com.example.bookreviewsver.src.Main.SignUp.service

import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.SignUp.`interface`.SignUpActivityView
import com.example.bookreviewsver.src.Main.SignUp.`interface`.SignUpRetrofitInterface
import com.example.bookreviewsver.src.Main.SignUp.model.SignUpBody
import com.example.bookreviewsver.src.Main.SignUp.model.SignUpResponse
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpService(val view: SignUpActivityView) {

    fun PostSignUp(id: String, pw: String, nickname:String, email:String) {
        val signUpInterface: SignUpRetrofitInterface? = MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(SignUpRetrofitInterface::class.java)
        signUpInterface?.postSignUp(SignUpBody(id, pw, nickname, email))?.enqueue(object : Callback<SignUpResponse?> {
            override fun onResponse(call: Call<SignUpResponse?>, response: Response<SignUpResponse?>) {
                Log.d("PostSignIn", "성공")
                view.SignUpSuccess(response.body() as SignUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse?>, t: Throwable) {
                view.SignUpFailure(t.message ?: "통신오류")
            }

        })
    }


}