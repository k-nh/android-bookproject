package com.example.bookreviewsver.src.report.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityJoinBinding
import com.example.bookreviewsver.src.Main.Chat.UserModel
import com.example.bookreviewsver.src.Main.SignIn.model.CheckIdResponse
import com.example.bookreviewsver.src.Main.SignUp.`interface`.SignUpActivityView
import com.example.bookreviewsver.src.Main.SignUp.model.SignUpResponse
import com.example.bookreviewsver.src.Main.SignUp.service.SignUpService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class JoinActivity : BaseActivity<ActivityJoinBinding>(ActivityJoinBinding::inflate) , SignUpActivityView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val joinButton = binding.btnJoinComplete as Button

        binding.btnJoinId.setOnClickListener {
            val id: String = binding.etJoinId.text.toString()
            SignUpService(this).checkId(id)
        }

        joinButton!!.setOnClickListener {
            try{
                showLoadingDialog(this)
                val id: String = binding.etJoinId.text.toString()
                val pw1: String = binding.etJoinPassword.text.toString()
                val pw2:String = binding.etJoinPassword2.text.toString()
                val email:String = binding.etJoinEmail.text.toString()
                var pw: String? = null
                if (pw1 != pw2){
                    showCustomToast("비밀번호가 일치하지 않습니다.")
                }
                else {
                    pw = pw1
                }
                if (id != null && pw != null && email!= null) {
                    SignUpService(this).PostSignUp(id,pw,"",email)
                    val userModel = UserModel()
                    userModel.userName = id
                    userModel.userProfileImg = "http://www.dosan21.kr/common/img/default_profile.png"
                    FirebaseDatabase.getInstance().getReference().child("users").child(id).setValue(userModel)
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

    override fun checkIdSuccess(checkIdResponse: CheckIdResponse) {
        if (checkIdResponse.isSuccess)
            showCustomToast("사용 가능한 아이디입니다.")
        else
            showCustomToast("이미 존재하는 아이디입니다.")
    }
}