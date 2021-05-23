package com.example.bookreviewsver.src.Main.MyPage.model

import com.google.gson.annotations.SerializedName

data class EditInfoBody (
    @SerializedName("uri") val uri : String,
    @SerializedName("nickname") val nickname : String
    )
