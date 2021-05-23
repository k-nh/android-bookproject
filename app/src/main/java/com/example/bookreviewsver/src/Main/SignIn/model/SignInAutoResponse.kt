package com.example.bookreviewsver.src.Main.SignIn.model

import com.google.gson.annotations.SerializedName

data class SignInAutoResponse (
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code : Int,
        @SerializedName("message") val message: String
        )
