package com.example.bookreviewsver.src.Main.Report.model

import com.google.gson.annotations.SerializedName

data class EditReportBody (
    @SerializedName("title") val title : String,
    @SerializedName("content") val content : String
    )
