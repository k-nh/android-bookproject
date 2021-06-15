package com.example.bookreviewsver.src.Main.SignUp.`interface`

import com.example.bookreviewsver.src.Main.SignUp.model.SignUpBody
import com.example.bookreviewsver.src.Main.SignUp.model.SignUpResponse
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.Main.SignIn.model.CheckIdBody
import com.example.bookreviewsver.src.Main.SignIn.model.CheckIdResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpRetrofitInterface {

    @POST(API.POST_SIGNUP)
    fun postSignUp(@Body params: SignUpBody): Call<SignUpResponse>


    @POST("checkId")
    fun checkId(@Body params: CheckIdBody): Call<CheckIdResponse>
}