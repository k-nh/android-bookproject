package com.example.bookreviewsver.src.Main.SignUp.model

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("result") val result: Result
)