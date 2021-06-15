
package com.example.bookreviewsver.src.home

import OtherPageActivityView
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication.Companion.sSharedPreferences
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityOtherpageBinding
import com.example.bookreviewsver.src.Main.MyPage.LikeListAdapter
import com.example.bookreviewsver.src.Main.MyPage.ReadListAdapter
import com.example.bookreviewsver.src.Main.MyPage.ReadingListAdapter
import com.example.bookreviewsver.src.Main.MyPage.model.*
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.model.Result
import com.example.bookreviewsver.src.Main.Report.service.ReportService
import com.example.bookreviewsver.src.Main.SignUp.service.OtherPageService
import com.example.bookreviewsver.src.report.LikeReportAdapter
import com.example.bookreviewsver.src.report.ReportAdapter
import com.example.bookreviewsver.src.report.login.LoginMainActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class OtherPageActivity : BaseActivity<ActivityOtherpageBinding>(ActivityOtherpageBinding::inflate),OtherPageActivityView, ReportActivityView{
    var userId: String? = null
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var likeReportAdapter: LikeReportAdapter
    private lateinit var likeListAdapter: LikeListAdapter
    private lateinit var readListAdapter: ReadListAdapter
    private lateinit var readingListAdapter: ReadingListAdapter
    private var mRecyclerView: RecyclerView? = null
    private var likeRecyclerView: RecyclerView? = null
    private var otherReportArrayList = ArrayList<Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.toolbar_empty)

        mRecyclerView = binding.rvOtherpageBookReport
        likeRecyclerView = binding.rvOtherLikeBook
        userId = intent.getStringExtra("userId")

        OtherPageService(this).getOtherUserInfo(userId!!)
        OtherPageService(this).getOtherLikeList(userId!!)
        ReportService(this).getBoardList()


        binding.otherCheckList.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab?.position
                if (position == 0)
                    getOtherCheckList(0)
                else if (position == 1)
                    getOtherCheckList(1)
                else if (position == 2)
                    getOtherCheckList(2)
                else if (position == 3)
                    getOtherCheckList(3)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }


    private fun getOtherCheckList(index:Int){
        if(index == 0) {
            binding.tvNotice.visibility = View.GONE
            OtherPageService(this).getOtherLikeList(userId!!)
        }
        else if(index == 1){
            binding.tvNotice.visibility = View.GONE
            OtherPageService(this).getReadingList(userId!!)
        }
        else if(index == 2){
            binding.tvNotice.visibility = View.GONE
            OtherPageService(this).getReadList(userId!!)
        }
    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        this.reportAdapter = ReportAdapter()
        for (i in reportResponse.result) {
            if (i.id == userId && !i.secret) {
                if(!otherReportArrayList.contains(i)){
                    this.otherReportArrayList.add(i)
                }
            }
        }
        if(otherReportArrayList.isEmpty()){
            binding.noticeOtherPage.visibility = View.VISIBLE
        }
        this.reportAdapter.submitList(otherReportArrayList)
        mRecyclerView!!.layoutManager = GridLayoutManager(
                this,
                1,
                GridLayoutManager.VERTICAL,
                false
        )
        mRecyclerView!!.adapter = this.reportAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_mypage_logout -> {
                val intent = Intent(this, LoginMainActivity::class.java)
                startActivity(intent)
                sSharedPreferences.edit().putInt("userIdx", 0).apply()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun reportListFailure(message: String) {
        Log.d("ReportListFailure", "ReportListFailure!!")
    }

    override fun postReportSuccess(postReportResponse: PostReportResponse) {
        TODO("Not yet implemented")
    }

    override fun postReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun getReportSuccess(getReportResponse: GetReportResponse) {
        TODO("Not yet implemented")
    }

    override fun getReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun editReportSuccess(editReportResponse: EditReportResponse) {
        TODO("Not yet implemented")
    }

    override fun editCommentSuccess(editCommentResponse: EditCommentResponse) {
        TODO("Not yet implemented")
    }

    override fun postCommentSuccss(postCommentResponse: PostCommentResponse) {
        TODO("Not yet implemented")
    }

    override fun deleteReportSuccss() {
        TODO("Not yet implemented")
    }

    override fun getCommentSuccss(getCommentResponse: GetCommentResponse) {
        TODO("Not yet implemented")
    }

    override fun getLikeFeedListSuccss(likeFeedListResponse: LikeFeedListResponse) {
        TODO("Not yet implemented")
    }

    override fun getLikeFeedIdListSuccss(likeFeedIdListResponse: LikeFeedIdListResponse) {
        TODO("Not yet implemented")
    }

    override fun otherPageSuccess(myPageResponse: MyPageResponse) {
        binding.otherProfileNickname.text = myPageResponse.result.id
        binding.otherGrade.text = myPageResponse.grade
        binding.tvLikeBook.text = myPageResponse.favoriteCnt.toString()
        binding.tvReadBook.text = myPageResponse.readCnt.toString()
        binding.tvReadingBook.text = myPageResponse.readingCnt.toString()

        if(myPageResponse.result.userImgUrl== null){
            binding.otherProfile.setImageResource(R.drawable.default_profile)
        }else{
            Glide.with(this).load(myPageResponse.result.userImgUrl).into(binding.otherProfile)
        }    }

    override fun otherLikeListSuccess(likeListResponse: LikeListResponse) {
        Log.d("likeListSuccess", "likeListSuccess")
        this.likeListAdapter = LikeListAdapter()
        if(likeListResponse.result.isEmpty()){
            binding.tvNotice.visibility = View.VISIBLE
            binding.tvNotice.text = "읽을 도서를 추가해주세요!"
        }
        if(likeListResponse.result != null )
            this.likeListAdapter.submitLikeList(likeListResponse.result as ArrayList<LikeListResult>)
        likeRecyclerView!!.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        likeRecyclerView!!.adapter = this.likeListAdapter    }

    override fun otherReadListSuccess(readListResponse: ReadListResponse) {
        Log.d("getReadListSuccess", "getReadListSuccess")
        this.readListAdapter = ReadListAdapter()
        if(readListResponse.result.isEmpty()){
            binding.tvNotice.visibility = View.VISIBLE
            binding.tvNotice.text = "읽은 도서를 추가해주세요!"
        }
        if(readListResponse != null )
            this.readListAdapter.submitReadList(readListResponse.result as ArrayList<ReadListResult>)
        likeRecyclerView!!.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        likeRecyclerView!!.adapter = this.readListAdapter    }

    override fun otherReadingListSuccess(readingListResponse: ReadingListResponse) {
        Log.d("getReadingListSuccess", "getReadingListSuccess")
        this.readingListAdapter = ReadingListAdapter()
        if(readingListResponse.result.isEmpty()){
            binding.tvNotice.visibility = View.VISIBLE
            binding.tvNotice.text = "읽고 있는 도서를 추가해주세요!"
        }
        if(readingListResponse != null )
            this.readingListAdapter.submitReadingList(readingListResponse.result as ArrayList<ReadingListResult>)
        likeRecyclerView!!.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        likeRecyclerView!!.adapter = this.readingListAdapter    }
        }