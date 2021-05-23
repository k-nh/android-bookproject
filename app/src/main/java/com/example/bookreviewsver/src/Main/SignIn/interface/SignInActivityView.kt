package com.example.bookreviewsver.src.Main.SignIn.`interface`

import com.example.bookreviewsver.src.Main.SignIn.model.SignInAutoResponse
import com.example.bookreviewsver.src.Main.SignIn.model.SignInResponse

interface SignInActivityView {
    fun validateSuccess(signInResponse: SignInResponse)

    fun validateFailure(message: String?)

    fun SignInAutoSuccess(SignInAutoResponse: SignInAutoResponse)

    fun SignInAutoFailure(message: String?)

}