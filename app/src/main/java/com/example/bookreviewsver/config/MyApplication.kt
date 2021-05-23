package com.example.application.config

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.XAccessTokenInterceptor
import com.example.bookreviewsver.src.Main.Home.`interface`.isJsonArray
import com.example.bookreviewsver.src.Main.Home.`interface`.isJsonObject
import com.kakao.sdk.common.KakaoSdk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


class MyApplication : Application() {
    //앱이 최초로 실행할때 한번만 생성되면 되는 코드 넣기 //싱글톤 객체
    companion object {
        //context 얻기 위함
        lateinit var instance: MyApplication
                private set

        //만들어져있는 SharedPreferences 사용 //재생성x
        lateinit var sSharedPreferences: SharedPreferences

        // JWT Token 헤더 키 값
        var X_ACCESS_TOKEN = "X_ACCESS_TOKEN"


    }
    object RetrofitClient {
        private var retrofitClient: Retrofit? = null

        //RetrofitClient 가져오기
        fun getCleint(Url: String): Retrofit? {
            Log.d(ContentValues.TAG, "retrofitclient - getclient called")
            val client = OkHttpClient.Builder()
            //log 를 찍기 위해 logging intercepter 설정
            val loggingIntercepter = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d(ContentValues.TAG, "retrofitClient - log() Called! / message")

                    when {
                        message.isJsonObject() ->
                            Log.d(ContentValues.TAG, JSONObject(message).toString(4))

                        message.isJsonArray() ->
                            Log.d(ContentValues.TAG, JSONObject(message).toString(4))
                        else -> {
                            try {
                                Log.d(ContentValues.TAG, JSONObject(message).toString(4))
                            } catch (e: Exception) {
                                Log.d(ContentValues.TAG, message)
                            }
                        }
                    }
                }
            })

            //위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가
            loggingIntercepter.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(loggingIntercepter)

            //커넥션 타임아웃
            client.connectTimeout(10, TimeUnit.SECONDS)
            client.readTimeout(10, TimeUnit.SECONDS)
            client.writeTimeout(10, TimeUnit.SECONDS)
            client.retryOnConnectionFailure(true)
            client.addNetworkInterceptor(XAccessTokenInterceptor())

            if (retrofitClient == null) {
                retrofitClient = Retrofit.Builder()
                        .baseUrl(Url)
                        .addConverterFactory(GsonConverterFactory.create())
                        //위에서 설정한 클라이언트(okhttp)로 레트로핏 클라이언트를 설정!
                        .client(client.build())
                        .build()
            }
            return retrofitClient
        }
    }

    object RetrofitClient2 {
        private var retrofitClient: Retrofit? = null

        //RetrofitClient 가져오기
        fun getCleint2(Url: String): Retrofit? {
            Log.d(ContentValues.TAG, "retrofitclient - getclient called")
            val client = OkHttpClient.Builder()
            //log 를 찍기 위해 logging intercepter 설정
            val loggingIntercepter = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d(ContentValues.TAG, "retrofitClient - log() Called! / message")

                    when {
                        message.isJsonObject() ->
                            Log.d(ContentValues.TAG, JSONObject(message).toString(4))

                        message.isJsonArray() ->
                            Log.d(ContentValues.TAG, JSONObject(message).toString(4))
                        else -> {
                            try {
                                Log.d(ContentValues.TAG, JSONObject(message).toString(4))
                            } catch (e: Exception) {
                                Log.d(ContentValues.TAG, message)
                            }
                        }
                    }
                }
            })

            //위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가
            loggingIntercepter.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(loggingIntercepter)


            //커넥션 타임아웃
            client.connectTimeout(10, TimeUnit.SECONDS)
            client.readTimeout(10, TimeUnit.SECONDS)
            client.writeTimeout(10, TimeUnit.SECONDS)
            client.retryOnConnectionFailure(true)
            client.addNetworkInterceptor(XAccessTokenInterceptor())

            if (retrofitClient == null) {
                retrofitClient = Retrofit.Builder()
                        .baseUrl(Url)
                        .addConverterFactory(GsonConverterFactory.create())
                        //위에서 설정한 클라이언트(okhttp)로 레트로핏 클라이언트를 설정!
                        .client(client.build())
                        .build()
            }
            return retrofitClient
        }
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
        instance = this
        sSharedPreferences = applicationContext.getSharedPreferences("app", Context.MODE_PRIVATE)

    }

}