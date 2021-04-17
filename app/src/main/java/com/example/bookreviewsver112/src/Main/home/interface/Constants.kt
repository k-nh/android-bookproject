package com.example.bookreviewsver112.src.Main.home.`interface`

object Constants{
    const val TAG : String = "Log"
}

object API{
    const val BASE_URL : String = "https://book.interpark.com/api/"
    const val CLIENT_ID : String = "C3695C0CB1DF5D86A460998D4C0DEFFA558081E91FB6484162C38261119EB3DF"
    const val GET_BEST_SELLER : String = "bestSeller.api"
    const val GET_SEARCH_BOOK : String = "search.api"



}

enum class RESPONSE_STATE{
    OKAY,
    FAIL
}