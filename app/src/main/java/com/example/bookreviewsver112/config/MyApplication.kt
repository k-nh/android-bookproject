package com.example.bookreviewsver112.config

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.bookreviewsver112.R
import com.kakao.sdk.common.KakaoSdk
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MyApplication : Application() {
    //앱이 최초로 실행할때 한번만 생성되면 되는 코드 넣기 //싱글톤 객체
    // 테스트 서버 주소
    var API_URL = "http://52.79.184.54:8080/BookMVC/"

    // 실서버 주소
    //    var API_URL = "https://template.softsquared.com/";

    companion object {
        //만들어져있는 SharedPreferences 사용 //재생성x
        lateinit var sSharedPreferences: SharedPreferences

        // JWT Token 헤더 키 값
        var X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

        //retrofit 인스턴스 //한번만 생성 후 사용
        lateinit var sRetrofit: Retrofit

    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = applicationContext.getSharedPreferences("app", Context.MODE_PRIVATE)
        //레트로핏 인스턴스 생성
        //initRetrofitInstance()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
//
//    //레트로핏 인스턴스 생성 및 설정값 지정
//    //연결 타임아웃 5초, HttpLoggingInterceptor로 어떤 요청이 들어가고 나오는지 보여줌
//    private fun initRetrofitInstance(){
//        val client : OkHttpClient = OkHttpClient.Builder()
//            .readTimeout(5000, TimeUnit.MILLISECONDS)
//            .connectTimeout(5000, TimeUnit.MILLISECONDS)
//            .addInterceptor(HttpLoggingInterceptor {message: String->
//            Log.d("network_info",message)
//            }.setLevel(HttpLoggingInterceptor.Level.BODY))
//            .build()

        //sRetrofit이라는 전역변수에 api url,인터셉터,gson을 넣어주고 빌드해주는 코드
        //이 전역변수로 http 요청을 서버로 보내면 된다
//        sRetrofit = Retrofit.Builder()
//            .baseUrl(API_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

//    private fun HttpLoggingInterceptor(logger: (String) -> Int): HttpLoggingInterceptor {
//        TODO("Not yet implemented")
//    }


}