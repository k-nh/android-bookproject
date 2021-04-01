package com.example.bookreviewsver112.src.Find

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.config.BaseActivity
import com.example.bookreviewsver112.databinding.ActivityFindId2Binding
import com.example.bookreviewsver112.databinding.ActivityFindPassword2Binding
import com.example.bookreviewsver112.src.report.login.LoginMainActivity
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