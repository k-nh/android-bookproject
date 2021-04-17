package com.example.bookreviewsver112.src.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.config.BaseFragment
import com.example.bookreviewsver112.databinding.FragmentHomeBinding
import com.example.bookreviewsver112.src.Main.home.BestSellerAdapter
import com.example.bookreviewsver112.src.Main.home.SearchAdapter
import com.example.bookreviewsver112.src.Main.home.`interface`.API
import com.example.bookreviewsver112.src.Main.home.`interface`.Constants.TAG
import com.example.bookreviewsver112.src.Main.home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver112.src.Main.home.`interface`.HomeRetrofitManager


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


        getBestSeller(100)

        binding.btnDomesticBook.setOnClickListener(View.OnClickListener {
            getBestSeller(100)
        })


        binding.btnForeignBook.setOnClickListener(View.OnClickListener {
            getBestSeller(200)
        })

        binding.btnSearch.setOnClickListener(View.OnClickListener {
            val searchContent = binding.svSearch.getQuery().toString()
            Log.d(TAG, "검색어 : $searchContent")
            getSearchBook(searchContent)
            binding.homeTitle.visibility = View.GONE
        })


    }

    private fun getBestSeller(categoryId : Int) {
        HomeRetrofitManager.instance.getBestSeller(
                key = API.CLIENT_ID,
                categoryId = categoryId,
                output = "json"
        ) { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(TAG, "api 호출 성공 : $responseArrayList")

                    this.bestSellerRecyclerViewAdapter = BestSellerAdapter()
                    if (responseArrayList != null) {
                        this.bestSellerRecyclerViewAdapter.submitList(responseArrayList)
                    }

                    mRecyclerView!!.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                    mRecyclerView!!.adapter = this.bestSellerRecyclerViewAdapter


                }
                RESPONSE_STATE.FAIL -> {
                    showCustomToast("호출 에러입니다")
                }

            }
        }
    }

    private fun getSearchBook(query : String) {
        HomeRetrofitManager.instance.getSearchBook(
                key = API.CLIENT_ID,
                query = query,
                output = "json")
        { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(TAG, "getSearchBook api 호출 성공 : $responseArrayList")

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

}