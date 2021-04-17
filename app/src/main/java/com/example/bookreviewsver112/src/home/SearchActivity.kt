//package com.example.bookreviewsver112.src.home
//
//import android.content.Intent.getIntent
//import android.util.Log
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.bookreviewsver112.src.Main.home.BestSellerAdapter
//import com.example.bookreviewsver112.src.Main.home.SearchAdapter
//import com.example.bookreviewsver112.src.Main.home.`interface`.API
//import com.example.bookreviewsver112.src.Main.home.`interface`.Constants
//import com.example.bookreviewsver112.src.Main.home.`interface`.HomeRetrofitManager
//import com.example.bookreviewsver112.src.Main.home.`interface`.RESPONSE_STATE
//
//class SearchActivity {
//
//    private fun getSearchBook(query : String) {
//        HomeRetrofitManager.instance.getSearchBook(
//                key = API.CLIENT_ID,
//                query = ,
//                output = "json"
//        ) { responseState, responseArrayList ->
//            when (responseState) {
//                RESPONSE_STATE.OKAY -> {
//                    //ArrayList 들어옴
//                    Log.d(Constants.TAG, "api 호출 성공 : $responseArrayList")
//
//                    this.searchRecyclerViewAdapter = SearchAdapter()
//                    if (responseArrayList != null) {
//                        this.searchRecyclerViewAdapter.submitList(responseArrayList)
//                    }
//
//                    mRecyclerView!!.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
//                    mRecyclerView!!.adapter = this.searchRecyclerViewAdapter
//
//
//                }
//                RESPONSE_STATE.FAIL -> {
//                    showCustomToast("호출 에러입니다")
//                }
//
//            }
//        }
//}
