package com.example.bookreviewsver.src.Main.SignUp.`interface`

import com.example.bookreviewsver.src.Main.SignUp.model.SignUpResponse

interface SignUpActivityView {
    fun SignUpSuccess(SignUpResponse: SignUpResponse)

    fun SignUpFailure(message: String?)

}