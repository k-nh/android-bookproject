package com.example.bookreviewsver.src.home

import MyPageFragmentView
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentRecommendBinding
import com.example.bookreviewsver.src.Main.Home.BestSellerAdapter
import com.example.bookreviewsver.src.Main.Home.SearchAdapter
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver.src.Main.Home.service.HomeRetrofitManager
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.LikeResult
import com.example.bookreviewsver.src.Main.MyPage.model.MyPageResponse
import com.example.bookreviewsver.src.Main.SignUp.service.MyPageService

class RecommendFragment : BaseFragment<FragmentRecommendBinding>(FragmentRecommendBinding::bind, R.layout.fragment_recommend),MyPageFragmentView{
    //어댑터
    private var mRecyclerView: RecyclerView? = null
    var categoryId = 0;
    private lateinit var bestSellerRecyclerViewAdapter : BestSellerAdapter
    var userId: String? = MyApplication.sSharedPreferences.getString("userId",null)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRecommendId.text = userId
        MyPageService(this).getLikeList()
        mRecyclerView = view.findViewById<View>(R.id.rv_recommend_book) as RecyclerView

    }

    override fun myPageSuccess(myPageResponse: MyPageResponse) {
        TODO("Not yet implemented")
    }

    override fun myPageFailure(message: String?) {
        TODO("Not yet implemented")
    }

    override fun likeListSuccess(likeListResponse: LikeListResponse) {
        Log.d("likeListSuccess","likeListSuccess")
        categoryId = likeListResponse.result[likeListResponse.result.size-1].categoryID
        getBestSeller(categoryId)

    }

    override fun likeListFailure(message: String?) {
        TODO("Not yet implemented")
    }


    private fun getBestSeller(categoryId : Int) {
        HomeRetrofitManager.instance.getBestSeller(
            key = BOOKAPI.CLIENT_ID,
            categoryId = categoryId,
            inputEncoding="euc-kr",
            output = "json"
        ) { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(Constants.TAG, "getBestSeller api 호출 성공")

                    this.bestSellerRecyclerViewAdapter = BestSellerAdapter()
                    if (responseArrayList != null) {
                        this.bestSellerRecyclerViewAdapter.submitList(responseArrayList)
                    }

                    mRecyclerView!!.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
                    mRecyclerView!!.adapter = this.bestSellerRecyclerViewAdapter


                }
                RESPONSE_STATE.FAIL -> { showCustomToast("호출 에러입니다") }

            }
        }
    }

}