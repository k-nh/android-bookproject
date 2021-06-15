package com.example.bookreviewsver.src.Main.Home.`interface`

fun String?.isJsonObject():Boolean{
    return this?.startsWith("{") == true && this.endsWith("}")
}

fun String?.isJsonArray():Boolean{
    return this?.startsWith("[") == true && this.endsWith("]")

}