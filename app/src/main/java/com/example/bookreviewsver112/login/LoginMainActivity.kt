package com.example.bookreviewsver112.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.databinding.ActivityLoginBinding
import com.example.bookreviewsver112.home.HomeActivity
import java.lang.Exception

class LoginMainActivity : AppCompatActivity(){
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val loginButton = binding.btnLogin as Button
        val idButton = binding.btnFindId as Button
        val passwordButton = binding.btnFindPassword as Button
        val joinButton = binding.btnJoin as Button

        loginButton!!.setOnClickListener {
            try{
                //HomeActivity 로 전환
                val homeIntent = Intent(this, HomeActivity::class.java)

                startActivity(homeIntent)
            }catch(e: Exception){
                e.printStackTrace()
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
}