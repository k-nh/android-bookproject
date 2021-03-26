package com.example.bookreviewsver112.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.databinding.ActivityFindPassword2Binding
import java.lang.Exception

class FindPassword2Activity : AppCompatActivity() {
    lateinit var binding : ActivityFindPassword2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPassword2Binding.inflate(layoutInflater)

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