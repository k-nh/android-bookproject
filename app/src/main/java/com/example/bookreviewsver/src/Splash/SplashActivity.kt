package com.example.bookreviewsver.src.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivitySplashBinding
import com.example.bookreviewsver.src.report.login.LoginMainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },1500)

    }
}