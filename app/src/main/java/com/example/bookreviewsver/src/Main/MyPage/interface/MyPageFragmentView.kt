import com.example.bookreviewsver.src.Main.MyPage.model.*

interface MyPageFragmentView {
    fun myPageSuccess(myPageResponse: MyPageResponse)
    fun editMyPageSuccess(editMyPageResponse: EditMyPageResponse)

    fun likeListSuccess(likeListResponse: LikeListResponse)
    fun getReadListSuccess(readListResponse: ReadListResponse)
    fun getReadingListSuccess(readingListResponse: ReadingListResponse)

}