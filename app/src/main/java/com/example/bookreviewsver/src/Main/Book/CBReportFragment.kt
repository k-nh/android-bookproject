package com.example.bookreviewsver.src.Main.Book

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentCbReportListBinding
import com.example.bookreviewsver.src.Main.Chat.ChatActivity
import com.example.bookreviewsver.src.Main.OCR.ReportExtractionActivity
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService
import com.example.bookreviewsver.src.report.ReportAdapter
import com.example.bookreviewsver.src.report.ReportListFragment
import com.example.bookreviewsver.src.report.ReportWriteActivity

class CBReportFragment : BaseFragment<FragmentCbReportListBinding>(FragmentCbReportListBinding::bind, R.layout.fragment_cb_report_list),
    ReportActivityView {
    //어댑터
    private lateinit var reportAdapter: ReportAdapter
    private var mRecyclerView: RecyclerView? = null
    var bookId = MyApplication.sSharedPreferences.getInt("bookId", 0)
    private var title : String? = MyApplication.sSharedPreferences.getString("title",null)
    private var bookImgUrl : String? = MyApplication.sSharedPreferences.getString("bookImgUrl",null)
    private var isbn : String? = MyApplication.sSharedPreferences.getString("isbn",null)
    private var privateReportArrayList = ArrayList<Result>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val mSwipeRefreshLayout= binding.swipeLayout1
        mSwipeRefreshLayout.setOnRefreshListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment2, CBReportFragment())
                .commitAllowingStateLoss()
            mSwipeRefreshLayout.isRefreshing = false
        }
        mRecyclerView = view.findViewById<View>(R.id.rv_book_detail_report_list) as RecyclerView
        ReportService(this).getBoardList()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.book_report_tb_items, menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id. menu_new_feed-> {
                try{
                    //ReportWriteActivity 로 전환
                    val intent = Intent(activity, ReportWriteActivity::class.java)
                    intent.putExtra("bookTitle",title)
                    intent.putExtra("bookImgUrl",bookImgUrl)
                    intent.putExtra("bookId",bookId)
                    intent.putExtra("isbn",isbn)
                    startActivity(intent)
                }catch(e: Exception){
                    e.printStackTrace()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        this.reportAdapter = ReportAdapter()

        Log.d("ReportListSuccess 책 id", bookId.toString())
        for (i in reportResponse.result) {
            if (i.bookID == bookId) {
                Log.d("ReportListSuccess", "아이디 맞는거")
                if(!privateReportArrayList.contains(i)){
                    this.privateReportArrayList.add(i)
                }
            }
        }

        this.reportAdapter.submitList(privateReportArrayList)
        mRecyclerView!!.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        mRecyclerView!!.adapter = this.reportAdapter

        if(privateReportArrayList.size == 0){
            binding.cbReportNotice.visibility = View.VISIBLE
        }
    }

    override fun reportListFailure(message: String) {
        Log.d("ReportListFailure", "ReportListFailure")
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


}
