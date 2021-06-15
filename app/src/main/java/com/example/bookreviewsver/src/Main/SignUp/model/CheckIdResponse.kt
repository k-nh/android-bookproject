package com.example.bookreviewsver.src.Main.SignIn.model

import com.google.gson.annotations.SerializedName

data class CheckIdResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean
)

data class CheckIdBody(
        @SerializedName("id") val id : String
        )