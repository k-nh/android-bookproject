package com.example.bookreviewsver112.src.Find

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.config.BaseActivity
import com.example.bookreviewsver112.databinding.ActivityFindId2Binding
import com.example.bookreviewsver112.databinding.ActivityFindPassword2Binding
import com.example.bookreviewsver112.databinding.ActivityFindPasswordBinding
import java.lang.Exception

class FindPasswordActivity : BaseActivity<ActivityFindPasswordBinding>(ActivityFindPasswordBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nextButton = binding.btnFindPasswordNext as Button

        nextButton!!.setOnClickListener {
            try{
                //FindPasswrod2Activity 로 전환
                val findpassword2Intent = Intent(this, FindPassword2Activity::class.java)

                startActivity(findpassword2Intent)
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

        setContentView(binding.root)
    }
}