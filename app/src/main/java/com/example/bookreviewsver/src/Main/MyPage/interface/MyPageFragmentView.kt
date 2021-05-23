import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.MyPageResponse

interface MyPageFragmentView {
    fun myPageSuccess(myPageResponse: MyPageResponse)

    fun myPageFailure(message: String?)

    fun likeListSuccess(likeListResponse: LikeListResponse)

    fun likeListFailure(message: String?)


}