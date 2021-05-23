package com.example.bookreviewsver.src.Find

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityFindId2Binding
import com.example.bookreviewsver.src.report.login.LoginMainActivity
import java.lang.Exception

class FindId2Activity :  BaseActivity<ActivityFindId2Binding>(ActivityFindId2Binding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginButton = binding.btnFindIdLogin as Button
        val passwordButton = binding.btnFindIdPassword as Button

        loginButton!!.setOnClickListener {
            try{
                //LoginMainActivity 로 전환
                val loginIntent = Intent(this, LoginMainActivity::class.java)

                startActivity(loginIntent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

        passwordButton!!.setOnClickListener {
            try{
                //FindPasswordActivity 로 전환
                val findpasswordIntent = Intent(this, FindPasswordActivity::class.java)

                startActivity(findpasswordIntent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

        setContentView(binding.root)
    }
}