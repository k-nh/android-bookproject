package com.example.bookreviewsver.src.home.`interface`

import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.Main.SignIn.model.SignInAutoResponse
import com.example.bookreviewsver.src.Main.SignIn.model.SignInBody
import com.example.bookreviewsver.src.Main.SignIn.model.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignInRetrofitInterface {

    @POST(API.POST_SIGNIN)
    fun postSignIn(@Body params: SignInBody): Call<SignInResponse>

    @GET(API.GET_LOGIN_AUTO)
    fun getSignInAuto(): Call<SignInAutoResponse>

}