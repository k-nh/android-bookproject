package com.example.bookreviewsver.src.Main.Home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityBookDetailBinding
import com.example.bookreviewsver.src.Main.Chat.ChatActivity
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService
import com.example.bookreviewsver.src.home.ChatFragment
import com.example.bookreviewsver.src.report.ReportAdapter
import com.example.bookreviewsver.src.report.ReportListFragment
import com.example.bookreviewsver.src.report.ReportWriteActivity

class BookDetailActivity : BaseActivity<ActivityBookDetailBinding>(ActivityBookDetailBinding::inflate), ReportActivityView {

    private lateinit var reportAdapter: ReportAdapter
    private var mRecyclerView: RecyclerView? = null
    private var bookId: Int = 0
    private var title : String? = null
    private var coverImgUrl : String? = null
    private var buyLink:String? = null
    private var privateReportArrayList = ArrayList<Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ReportService(this).getBoardList()
        mRecyclerView = binding.rvMypageBookReport

        setSupportActionBar(binding.tbBookDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        bookId = intent.getIntExtra("bookId", 0)
        title = intent.getStringExtra("title")
        coverImgUrl = intent.getStringExtra("coverImgUrl")
        val author = intent.getStringExtra("author")
        val publisher = intent.getStringExtra("publisher")
        val description = intent.getStringExtra("description")
        val pubDate = intent.getStringExtra("pubDate")
        buyLink = intent.getStringExtra("link")

        Glide.with(this)
            .load(coverImgUrl).into(binding.ivBookImgDetail)
        binding.tvBookTitleDetail.text = title
        binding.tvSearchBookAuthor.text = author
        binding.tvSearchBookPublisher.text = publisher
        binding.tvDescriptionDetail.text = description
        binding.tvSearchBookPubdate.text = pubDate

        binding.btnSite.setOnClickListener {
            Log.d("링크",buyLink.toString())
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(buyLink.toString()))
            startActivity(intent)
        }


    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        this.reportAdapter = ReportAdapter()

        Log.d("ReportListSuccess 책 id", bookId.toString())
        for (i in reportResponse.result) {
            if (i.bookID == bookId) {
                Log.d("ReportListSuccess", "아이디 맞는거")
                this.privateReportArrayList.add(i)
            }
        }
        this.reportAdapter.submitList(privateReportArrayList)

        mRecyclerView!!.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        mRecyclerView!!.adapter = this.reportAdapter
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

    override fun getCommentSuccss(getCommentResponse: GetCommentResponse) {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.book_detail_tb_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_report_write -> {
                try {

                    //ReportWriteActivity 로 전환
                    val intent = Intent(this, ReportWriteActivity::class.java)
                    intent.putExtra("bookTitle", title)
                    intent.putExtra("bookImgUrl", coverImgUrl)
                    intent.putExtra("bookId", bookId)
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }

            android.R.id.home->{
                finish()
                return true
            }

            R.id.menu_chat->{
                try {
                    val bundle = Bundle()
                    bundle.putString("bookTitle", title)
                    bundle.putString("coverImgUrl", coverImgUrl)
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, ChatFragment())
                            .commitAllowingStateLoss()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}