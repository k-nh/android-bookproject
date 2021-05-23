package com.example.bookreviewsver.src.report.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityJoinBinding
import com.example.bookreviewsver.src.Main.SignUp.`interface`.SignUpActivityView
import com.example.bookreviewsver.src.Main.SignUp.model.SignUpResponse
import com.example.bookreviewsver.src.Main.SignUp.service.SignUpService
import java.lang.Exception


class JoinActivity : BaseActivity<ActivityJoinBinding>(ActivityJoinBinding::inflate) , SignUpActivityView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val joinButton = binding.btnJoinComplete as Button


        joinButton!!.setOnClickListener {
            try{
                showLoadingDialog(this)
                val id: String = binding.etJoinId.text.toString()
                val pw1: String = binding.etJoinPassword.text.toString()
                val pw2:String = binding.etJoinPassword2.text.toString()
                val nickname:String = binding.etJoinName.text.toString()
                val email:String = binding.etJoinEmail.text.toString()
                var pw: String? = null
                if (pw1 != pw2){
                    showCustomToast("비밀번호가 일치하지 않습니다.")
                }
                else {
                    pw = pw1
                }
                if (id != null && pw != null && nickname!= null && email!= null) {
                    SignUpService(this).PostSignUp(id,pw,nickname,email)
                }
                else{
                    showCustomToast("모든 항목을 입력해주십시오.")
                }
            }catch(e: Exception){
                e.printStackTrace()
            }
        }

    }

    override fun SignUpSuccess(SignUpResponse: SignUpResponse) {
        hideLoadingDialog()
        //LoginMainActivity 로 전환
        val loginIntent = Intent(this, LoginMainActivity::class.java)
        startActivity(loginIntent)

    }

    override fun SignUpFailure(message: String?) {
        hideLoadingDialog()
    }
}