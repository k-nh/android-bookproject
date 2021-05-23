package com.example.bookreviewsver.src.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentHomeBinding
import com.example.bookreviewsver.src.Main.Home.BestSellerAdapter
import com.example.bookreviewsver.src.Main.Home.SearchAdapter
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants.TAG
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver.src.Main.Home.service.HomeRetrofitManager


class HomeFragment : BaseFragment<FragmentHomeBinding>(
        FragmentHomeBinding::bind,
        R.layout.fragment_home
){

    //어댑터
    private lateinit var bestSellerRecyclerViewAdapter : BestSellerAdapter
    private lateinit var searchRecyclerViewAdapter: SearchAdapter
    private var mRecyclerView: RecyclerView? = null





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById<View>(R.id.recycler_bestseller_item) as RecyclerView
        extractBestSeller()

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
                    Log.d(TAG, "getBestSeller api 호출 성공")

                    this.bestSellerRecyclerViewAdapter = BestSellerAdapter()
                    if (responseArrayList != null) {
                        this.bestSellerRecyclerViewAdapter.submitList(responseArrayList)
                    }

                    mRecyclerView!!.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                    mRecyclerView!!.adapter = this.bestSellerRecyclerViewAdapter


                }
                RESPONSE_STATE.FAIL -> { showCustomToast("호출 에러입니다") }

            }
        }
    }

    private fun getSearchBook(query : String) {
        HomeRetrofitManager.instance.getSearchBook(
                key = BOOKAPI.CLIENT_ID,
                query = query,
                output = "json")
        { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(TAG, "getSearchBook api 호출 성공")

                    this.searchRecyclerViewAdapter = SearchAdapter()
                    if (responseArrayList != null) {
                        this.searchRecyclerViewAdapter.submitList(responseArrayList)
                    }

                    mRecyclerView!!.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
                    mRecyclerView!!.adapter = this.searchRecyclerViewAdapter

                }
                RESPONSE_STATE.FAIL -> {
                    showCustomToast("호출 에러입니다")
                }

            }
        }
    }

    private fun extractBestSeller(){

        getBestSeller(100)

        HomeRetrofitManager.instance.getBookLikeList()

        binding.btnDomesticBook.setOnClickListener(View.OnClickListener {
            getBestSeller(100)
        })


        binding.btnForeignBook.setOnClickListener(View.OnClickListener {
            getBestSeller(200)
        })

        binding.btnSearch.setOnClickListener(View.OnClickListener {
            val searchContent = binding.svSearch.getQuery().toString()
            getSearchBook(searchContent)
            binding.homeTitle.visibility = View.GONE
        })
    }

}