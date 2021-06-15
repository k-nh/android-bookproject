import com.example.bookreviewsver.src.Main.MyPage.model.*

interface OtherPageActivityView {
    fun otherPageSuccess(myPageResponse: MyPageResponse)
    fun otherLikeListSuccess(likeListResponse: LikeListResponse)
    fun otherReadListSuccess(readListResponse: ReadListResponse)
    fun otherReadingListSuccess(readingListResponse: ReadingListResponse)

}