package com.example.bookreviewsver.src.Main.SignIn.model

import com.google.gson.annotations.SerializedName

data class SignInBody(
        @SerializedName("id") val id : String,
        @SerializedName("pw") val pw: String
)
