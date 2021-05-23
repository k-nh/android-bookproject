package com.example.bookreviewsver.src.report.login

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import com.example.application.config.MyApplication.Companion.X_ACCESS_TOKEN
import com.example.application.config.MyApplication.Companion.sSharedPreferences
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityLoginBinding
import com.example.bookreviewsver.src.Main.SignIn.`interface`.SignInActivityView
import com.example.bookreviewsver.src.Main.SignIn.model.SignInAutoResponse
import com.example.bookreviewsver.src.Main.SignIn.model.SignInResponse
import com.example.bookreviewsver.src.Main.SignIn.service.SignInService
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginMainActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),SignInActivityView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val btnLogin = binding.btnLoginEnable

        btnLogin.setEnabled(false)
        binding.etLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable) {
                if (edit.toString().isNotEmpty()) {
                    // 버튼 상태 활성화.
                    btnLogin.setVisibility(View.VISIBLE)
                    btnLogin.setEnabled(true)
                } else {
                    // 버튼 상태 비활성화.
                    btnLogin.setVisibility(View.INVISIBLE)
                    btnLogin.setEnabled(false)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        btnLogin.setOnClickListener {
            try{
                val id: String = binding.etLoginId.text.toString()
                val pw: String = binding.etLoginPassword.text.toString()
                showLoadingDialog(this)
                Log.d("id, pw", id + pw)
                SignInService(this).PostSignIn(id, pw)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                //Login Fail
                Log.d("카카오 로그인", "실패")
            }
            else if (token != null) {
                //Login Success
                Log.d("카카오 로그인", "성공")
                val Intent = Intent(this, MainActivity::class.java)
                startActivity(Intent)
            }
        }

        binding.btnLoginKako.setOnClickListener{
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@LoginMainActivity)) {
                    loginWithKakaoTalk(this@LoginMainActivity, callback = callback)
                } else {
                    loginWithKakaoAccount(this@LoginMainActivity, callback = callback)
                }
            }
        }


//        binding.btnFindId.setOnClickListener {
//            try{
//                //FindIdActivity 로 전환
//
//                val idIntent = Intent(this, FindIdActivity::class.java)
//
//                startActivity(idIntent)
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//
//        binding.btnFindPassword.setOnClickListener {
//            try{
//                //FindPasswordActivity 로 전환
//                val passwordIntent = Intent(this, FindPasswordActivity::class.java)
//
//                startActivity(passwordIntent)
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }

        binding.btnJoin.setOnClickListener {
            try{
                //JoinActivity 로 전환
                val joinIntent = Intent(this, JoinActivity::class.java)
                startActivity(joinIntent)
            }catch (e: Exception){
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

    override fun validateSuccess(signInResponse: SignInResponse) {
        hideLoadingDialog()
        if (signInResponse!!.isSuccess) {
            showCustomToast("로그인 완료")
            val jwtResponse: String = signInResponse.result.jwt
            Log.d("x-access-token", signInResponse.result.jwt)
            //jwt 토큰 보냄
            sSharedPreferences.edit().putString(X_ACCESS_TOKEN, jwtResponse).apply()
            Log.d("x-access-token", X_ACCESS_TOKEN)

            //userIdx 저장
            val userIdx: Int = signInResponse.result.useridx
            sSharedPreferences.edit().putInt("userIdx", userIdx).apply()


            //userId 저장
            val userId: String = signInResponse.result.userId
            sSharedPreferences.edit().putString("userId", userId).apply()

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun validateFailure(message: String?) {
        hideLoadingDialog()
    }

    override fun SignInAutoSuccess(SignInAutoResponse: SignInAutoResponse) {

    }

    override fun SignInAutoFailure(message: String?) {
    }
}