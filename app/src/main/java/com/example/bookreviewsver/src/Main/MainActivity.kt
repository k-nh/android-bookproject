package com.example.bookreviewsver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.application.config.MyApplication
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityMainBinding
import com.example.bookreviewsver.src.Main.SignIn.`interface`.SignInActivityView
import com.example.bookreviewsver.src.Main.SignIn.model.SignInAutoResponse
import com.example.bookreviewsver.src.Main.SignIn.model.SignInResponse
import com.example.bookreviewsver.src.Main.SignIn.service.SignInService
import com.example.bookreviewsver.src.home.ChatFragment
import com.example.bookreviewsver.src.home.ExtractFragment
import com.example.bookreviewsver.src.home.HomeFragment
import com.example.bookreviewsver.src.home.MyPageFragment
import com.example.bookreviewsver.src.report.ReportListFragment
import com.example.bookreviewsver.src.report.login.LoginMainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), SignInActivityView {
    var mBackWait = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        var userIdx: Int = MyApplication.sSharedPreferences.getInt("userIdx", 0)
        //로그인 되어있는지 -> jwt 토큰 확인
        SignInService(this).getSignInAuto()
        if(userIdx ==0){
            //LoginMainActivity 로 전환
            val loginIntent = Intent(this, LoginMainActivity::class.java)
            startActivity(loginIntent)
        }

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, HomeFragment()).commitAllowingStateLoss()

        binding.nvHomeBottom.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.menu_bottom_home -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, HomeFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }

                        R.id.menu_bottom_report -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, ReportListFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }

                        R.id.menu_bottom_chat -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, ChatFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }

                        R.id.menu_bottom_recommend -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, ExtractFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }

                        R.id.menu_bottom_mypage -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, MyPageFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }
                    }
                    false
                })
    }

    override fun validateSuccess(signInResponse: SignInResponse) {
    }

    override fun validateFailure(message: String?) {
    }

    override fun SignInAutoSuccess(SignInAutoResponse: SignInAutoResponse) {
        if (SignInAutoResponse.isSuccess) {
            Log.d("test", "자동 로그인 성공")
        } else {
            Log.d("test", "자동 로그인 실패")
            val intent = Intent(this, LoginMainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun SignInAutoFailure(message: String?) {
        val intent = Intent(this, LoginMainActivity::class.java)
        startActivity(intent)
    }


    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis().toInt()
            showCustomToast("한번 더 누르면 종료됩니다.")
        } else {
            finish() //액티비티 종료
        }
    }
}