package com.example.bookreviewsver112.src.Find

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.config.BaseActivity
import com.example.bookreviewsver112.databinding.ActivityFindId2Binding
import com.example.bookreviewsver112.databinding.ActivityJoinBinding
import com.example.bookreviewsver112.src.report.login.LoginMainActivity
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