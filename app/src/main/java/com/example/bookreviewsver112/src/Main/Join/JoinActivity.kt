package com.example.bookreviewsver112.src.report.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.config.BaseActivity
import com.example.bookreviewsver112.databinding.ActivityJoinBinding
import java.lang.Exception


class JoinActivity : BaseActivity<ActivityJoinBinding>(ActivityJoinBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val joinButton = binding.btnJoinComplete as Button

        joinButton!!.setOnClickListener {
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