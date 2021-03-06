package com.example.bookreviewsver.src.Find

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityFindIdBinding
import java.lang.Exception

class FindIdActivity : BaseActivity<ActivityFindIdBinding>(ActivityFindIdBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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