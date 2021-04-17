package com.example.bookreviewsver112.src.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.config.BaseFragment
import com.example.bookreviewsver112.databinding.FragmentHomeBinding
import com.example.bookreviewsver112.databinding.FragmentRecommendBinding
import com.example.bookreviewsver112.src.Main.Recommend.FavoriteBookAdapter
import com.example.bookreviewsver112.src.Main.home.BestSellerAdapter
import com.example.bookreviewsver112.src.Main.home.`interface`.API
import com.example.bookreviewsver112.src.Main.home.`interface`.Constants
import com.example.bookreviewsver112.src.Main.home.`interface`.HomeRetrofitManager
import com.example.bookreviewsver112.src.Main.home.`interface`.RESPONSE_STATE

class RecommendFragment : BaseFragment<FragmentRecommendBinding>(FragmentRecommendBinding::bind, R.layout.fragment_recommend){
    //어댑터
    private lateinit var favoriteRecyclerViewAdapter : FavoriteBookAdapter
    private var mRecyclerView: RecyclerView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById<View>(R.id.recycler_favorite_item) as RecyclerView


    }

}