package com.example.bookreviewsver112.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.databinding.ActivityFindId2Binding
import java.lang.Exception

class FindId2Activity : AppCompatActivity(){
    lateinit var binding : ActivityFindId2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindId2Binding.inflate(layoutInflater)

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