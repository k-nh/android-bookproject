package com.example.bookreviewsver.src.Main.Book

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.DrawerHeaderBinding
import com.example.bookreviewsver.databinding.FragmentBookDetailBinding
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService
import com.example.bookreviewsver.src.report.ReportAdapter

class CustomDrawer: BaseFragment<DrawerHeaderBinding>(DrawerHeaderBinding::bind, R.layout.drawer_header){
    var userId: String? = MyApplication.sSharedPreferences.getString("userId",null)
    var userProfileImg: String? = MyApplication.sSharedPreferences.getString("userProfileImg",null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawerId = binding.drawerId
        val drawerImg = binding.drawerProfile
        drawerId.text = userId
        Glide.with(this)
            .load(userProfileImg).into(drawerImg)

    }

}