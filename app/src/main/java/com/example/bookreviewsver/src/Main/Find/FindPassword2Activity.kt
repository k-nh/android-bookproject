package com.example.bookreviewsver.src.Find

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityFindPassword2Binding
import com.example.bookreviewsver.src.report.login.LoginMainActivity
import java.lang.Exception

class FindPassword2Activity : BaseActivity<ActivityFindPassword2Binding>(ActivityFindPassword2Binding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginButton = binding.btnFindPasswordLogin as Button

        loginButton!!.setOnClickListener {
            try{
                //LoginMainActivity 로 전환
                val loginIntent = Intent(this, LoginMainActivity::class.java)

                startActivity(loginIntent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

        setContentView(binding.root)
    }
}