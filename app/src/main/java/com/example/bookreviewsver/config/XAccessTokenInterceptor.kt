package com.example.bookreviewsver.config


import android.util.Log
import com.example.application.config.MyApplication.Companion.X_ACCESS_TOKEN
import com.example.application.config.MyApplication.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString(X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("X_ACCESS_TOKEN", jwtToken)
            Log.d("Interceptor 토큰 값","" +jwtToken)
        }
        return chain.proceed(builder.build())
    }

}