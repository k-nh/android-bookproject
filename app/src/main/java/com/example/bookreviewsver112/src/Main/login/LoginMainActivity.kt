package com.example.bookreviewsver112.src.report.login

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import com.example.bookreviewsver112.MainActivity
import com.example.bookreviewsver112.config.BaseActivity
import com.example.bookreviewsver112.databinding.ActivityLoginBinding
import com.example.bookreviewsver112.src.Find.FindIdActivity
import com.example.bookreviewsver112.src.Find.FindPasswordActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import java.lang.Exception
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginMainActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginButton = binding.btnLogin as Button
        val idButton = binding.btnFindId as Button
        val KaKaoLoginButton = binding.btnLoginKako as Button
        val NaverLoginButton = binding.btnLoginNaver as Button
        val passwordButton = binding.btnFindPassword as Button
        val joinButton = binding.btnJoin as Button
        val naver_client_id = "NDTX9Q5B8b4mNpvfabMt"
        val naver_client_secret = "NDTX9Q5B8b4mNpvfabMt"
        val naver_client_name = "bookreviewsver112"

        loginButton!!.setOnClickListener {
            try{
                //MainActivity 로 전환
                val Intent = Intent(this, MainActivity::class.java)

                startActivity(Intent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                //Login Fail
                showCustomToast("로그인 실패.")
            }
            else if (token != null) {
                //Login Success
                showCustomToast("로그인 성공.")
                val Intent = Intent(this, MainActivity::class.java)
                startActivity(Intent)
            }
        }

        KaKaoLoginButton.setOnClickListener{
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@LoginMainActivity)) {
                    loginWithKakaoTalk(this@LoginMainActivity, callback = callback)
                } else {
                    loginWithKakaoAccount(this@LoginMainActivity, callback = callback)
                }
            }
        }



        idButton!!.setOnClickListener {
            try{
                //FindIdActivity 로 전환

                val idIntent = Intent(this, FindIdActivity::class.java)

                startActivity(idIntent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

        passwordButton!!.setOnClickListener {
            try{
                //FindPasswordActivity 로 전환
                val passwordIntent = Intent(this, FindPasswordActivity::class.java)

                startActivity(passwordIntent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

        joinButton!!.setOnClickListener {
            try{
                //JoinActivity 로 전환
                val joinIntent = Intent(this, JoinActivity::class.java)

                startActivity(joinIntent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
        setContentView(binding.root)
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}