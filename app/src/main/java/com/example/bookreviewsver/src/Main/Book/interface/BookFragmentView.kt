package com.example.bookreviewsver.src.Main.Home.`interface`

import com.example.bookreviewsver.src.Main.Book.model.CheckListResponse
import com.example.bookreviewsver.src.Main.Home.model.LikeResponse

interface BookFragmentView {
    fun postBookLikeSuccess(bookLikeResponse: LikeResponse)
    fun postBookLikeFailure(message: String?)


    fun getCheckListSuccess(checkListResponse: CheckListResponse)
    fun getCheckListFailure(message: String?)
}