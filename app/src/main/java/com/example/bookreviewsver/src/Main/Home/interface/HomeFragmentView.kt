package com.example.bookreviewsver.src.Main.Home.`interface`

import com.example.bookreviewsver.src.Main.Home.model.LikeResponse
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.MyPageResponse

interface HomeFragmentView {
    fun postBookLikeSuccess(bookLikeResponse: LikeResponse)
    fun postBookLikeFailure(message: String?)

    fun getLikeListSuccess(LikeListResponse: LikeListResponse)
    fun getLikeListFailure(message: String?)
}