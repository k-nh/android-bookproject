package com.example.bookreviewsver112.src.Main.home.`interface`

fun String?.isJsonObject():Boolean{
    return this?.startsWith("{") == true && this.endsWith("}")
}

fun String?.isJsonArray():Boolean{
    return this?.startsWith("[") == true && this.endsWith("]")

}