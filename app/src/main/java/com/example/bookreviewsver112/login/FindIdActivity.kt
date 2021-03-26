package com.example.bookreviewsver112.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.databinding.ActivityFindIdBinding
import java.lang.Exception

class FindIdActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindIdBinding.inflate(layoutInflater)

        val nextButton = binding.btnFindIdNext as Button

        nextButton!!.setOnClickListener {
            try{
                //FindId2Activity 로 전환
                val findid2Intent = Intent(this, FindId2Activity::class.java)

                startActivity(findid2Intent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

        setContentView(binding.root)
    }
}