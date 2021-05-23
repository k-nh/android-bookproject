package com.example.bookreviewsver.src.report

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityReportWriteBinding
import com.example.bookreviewsver.src.Main.Home.SearchAdapter
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver.src.Main.Home.service.HomeRetrofitManager
import com.example.bookreviewsver.src.Main.Report.OCR.ReportExtractionActivity
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService

class ReportWriteActivity : BaseActivity<ActivityReportWriteBinding>(ActivityReportWriteBinding::inflate),ReportActivityView {
    lateinit var titleText: TextView
    lateinit var contentsText: TextView
    var bookId = 0
    var coverImgUrl :String? = null
    var bookTitle :String? = null
    var userId: String? = MyApplication.sSharedPreferences.getString("userId", null)
    private lateinit var searchRecyclerViewAdapter: SearchAdapter
    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mRecyclerView = binding.rvSearchBook as RecyclerView

        setSupportActionBar(binding.tbReportWrite)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        bookTitle = intent.getStringExtra("bookTitle")
        coverImgUrl = intent.getStringExtra("bookImgUrl")
        bookId = intent.getIntExtra("bookId", 0)

        Glide.with(this)
                .load(coverImgUrl)
                .into(binding.imgReportWriteBook)

        binding.tvBookTitle.text = bookTitle
        titleText = binding.etReportTitle as EditText
        contentsText = binding.etReportContents as EditText

        if(bookTitle == null && coverImgUrl == null){
            binding.tvBookTitle.visibility = View.INVISIBLE
            binding.imgReportWriteBook.visibility = View.INVISIBLE
            binding.svWriteSearch.visibility = View.VISIBLE
            binding.btnWriteSearch.visibility = View.VISIBLE

            binding.btnWriteSearch.setOnClickListener(View.OnClickListener {
                mRecyclerView!!.visibility = View.VISIBLE
                binding.etReportTitle.visibility = View.INVISIBLE
                binding.etReportContents.visibility = View.INVISIBLE

                val searchContent = binding.svWriteSearch.getQuery().toString()
                HomeRetrofitManager.instance.getSearchBook(
                        key = BOOKAPI.CLIENT_ID,
                        query = searchContent,
                        output = "json")
                { responseState, responseArrayList ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            //ArrayList 들어옴
                            Log.d(Constants.TAG, "getSearchBook api 호출 성공")

                            this.searchRecyclerViewAdapter = SearchAdapter()
                            if (responseArrayList != null) {
                                this.searchRecyclerViewAdapter.submitList(responseArrayList)
                            }

                            mRecyclerView!!.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
                            mRecyclerView!!.adapter = this.searchRecyclerViewAdapter

                        }
                        RESPONSE_STATE.FAIL -> {
                            showCustomToast("호출 에러입니다")
                        }

                    }
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.report_write_tb_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_post_complete -> {
                try {
                    val titleData = titleText!!.text.toString()
                    val contentsData = contentsText!!.text.toString()
                    //데이터베이스에 데이터 저장
                    ReportService(this).postReport(titleData, bookId, bookTitle!!, contentsData, userId!!, coverImgUrl!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }
            R.id.menu_post_text -> {
                val Intent = Intent(this, ReportExtractionActivity::class.java)
                startActivity(Intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        TODO("Not yet implemented")
    }

    override fun reportListFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun postReportSuccess(postReportResponse: PostReportResponse) {
        showCustomToast("게시글을 등록하였습니다")
        //ReportPostActivity 로 전환
        val intent = Intent(this, ReportPostActivity::class.java)
        intent.putExtra("reportId", postReportResponse.result.repostId)
        startActivity(intent)
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

    override fun getCommentSuccss(getCommentResponse: GetCommentResponse) {
        TODO("Not yet implemented")
    }

}