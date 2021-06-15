package com.example.bookreviewsver.src.Main.SignUp.service

import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.SignUp.`interface`.SignUpActivityView
import com.example.bookreviewsver.src.Main.SignUp.`interface`.SignUpRetrofitInterface
import com.example.bookreviewsver.src.Main.SignUp.model.SignUpBody
import com.example.bookreviewsver.src.Main.SignUp.model.SignUpResponse
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.BASE_URL
import com.example.bookreviewsver.src.Main.SignIn.model.CheckIdBody
import com.example.bookreviewsver.src.Main.SignIn.model.CheckIdResponse
import com.example.bookreviewsver.src.Main.SignIn.model.SignInResponse
import com.example.bookreviewsver.src.home.`interface`.SignInRetrofitInterface
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

    fun checkId(id: String) {
        val signUpInterface: SignUpRetrofitInterface? = MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(
            SignUpRetrofitInterface::class.java)
        signUpInterface?.checkId(CheckIdBody(id))?.enqueue(object : Callback<CheckIdResponse?> {
            override fun onResponse(call: Call<CheckIdResponse?>, response: Response<CheckIdResponse?>) {
                Log.d("checkId", "중복체크")
                view.checkIdSuccess(response.body() as CheckIdResponse)
            }

            override fun onFailure(call: Call<CheckIdResponse?>, t: Throwable) {
            }
        })
    }


}