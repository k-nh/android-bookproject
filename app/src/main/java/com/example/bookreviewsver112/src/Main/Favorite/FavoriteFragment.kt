package com.example.bookreviewsver112.src.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.config.BaseFragment
import com.example.bookreviewsver112.databinding.FragmentFavoriteBinding
import com.example.bookreviewsver112.databinding.FragmentHomeBinding

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::bind, R.layout.fragment_favorite){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCustomToast("favorite")

    }

}