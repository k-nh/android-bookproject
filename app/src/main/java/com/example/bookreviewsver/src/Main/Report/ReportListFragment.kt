package com.example.bookreviewsver.src.report

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentReportListBinding
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService

class ReportListFragment : BaseFragment<FragmentReportListBinding>(FragmentReportListBinding::bind, R.layout.fragment_report_list), ReportActivityView{
    private lateinit var reportAdapter : ReportAdapter
    private var mRecyclerView: RecyclerView? = null
    private var likeFeedList = ArrayList<Int>()
    private var reportArrayList = ArrayList<Result>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val mSwipeRefreshLayout= binding.swipeLayout
        mSwipeRefreshLayout.setOnRefreshListener {
                requireFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, ReportListFragment())
                .commitAllowingStateLoss()
            mSwipeRefreshLayout.isRefreshing = false
        }
        ReportService(this).getFavoriteFeedList()
        (activity as MainActivity).supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        (activity as MainActivity).supportActionBar?.setCustomView(R.layout.toolbar_empty)
        mRecyclerView = view.findViewById<View>(R.id.rv_post_list) as RecyclerView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.report_list_tb_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_post_write -> {
                val intent = Intent(activity, ReportWriteActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun reportListSuccess(reportResponse: ReportResponse) {
        this.reportAdapter = ReportAdapter()
        for (i in reportResponse.result) {
            if (!i.secret) {
                if(likeFeedList.contains(i.repostId)) {
                    i.like = true
                }
                this.reportArrayList.add(i)
            }
        }
        this.reportAdapter.submitList(reportArrayList)

        mRecyclerView!!.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        mRecyclerView!!.adapter = this.reportAdapter

    }

    override fun reportListFailure(message: String) {
        TODO("Not yet implemented")
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
        for(i in likeFeedListResponse.result){
            Log.d("getLikeFeedListSuccss",i.repostId.toString())
            likeFeedList.add(i.repostId)
            for(i in likeFeedList){
                Log.d("LikeFeedList",i.toString()+"출력")
            }
        }
        ReportService(this).getBoardList()
    }

    override fun getLikeFeedIdListSuccss(likeFeedIdListResponse: LikeFeedIdListResponse) {
        TODO("Not yet implemented")
    }


}

