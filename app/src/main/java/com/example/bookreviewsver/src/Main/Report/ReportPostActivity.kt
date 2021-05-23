package com.example.bookreviewsver.src.report

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityReportPostBinding
import com.example.bookreviewsver.src.Main.Report.CommentAdapter
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService

class ReportPostActivity : BaseActivity<ActivityReportPostBinding>(ActivityReportPostBinding::inflate), ReportActivityView {
    private lateinit var commentAdapter: CommentAdapter
    private var mRecyclerView: RecyclerView? = null
    var booktitle: String? = null
    var writerId: String? = null
    var regDate: String? = null
    var titleData: String? = null
    var contentsData: String? = null
    var editTitleData: String? = null
    var editContentsData: String? = null
    var coverImgUrl: String? = null
    var reportId :Int = 0
    var userId: String? = MyApplication.sSharedPreferences.getString("userId", null)

    //result 변경해야함
    private var privateCommentArrayList = ArrayList<GetCommentResult>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbReportPost)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        reportId = intent.getIntExtra("reportId", 0)
        ReportService(this).getReport(reportId)
        mRecyclerView = binding.rvComment

        binding.btnWriteComment.setOnClickListener {
            try{
                val commentData: String = binding.etComment.text.toString()
                Log.d("댓글", userId.toString() + commentData)
                ReportService(this).postComment(reportId, commentData, userId!!)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        TODO("Not yet implemented")
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
        Log.d("getReportSuccess", "성공")
        booktitle = getReportResponse.result.bookName
        writerId = getReportResponse.result.id
        regDate = getReportResponse.result.regdate
        titleData = getReportResponse.result.title
        contentsData = getReportResponse.result.content
        coverImgUrl = getReportResponse.result.coverImgUrl
        reportId = getReportResponse.result.repostId


        binding.tvReportBookTitle.text = booktitle
        binding.tvWriterId.text = writerId
        binding.tvReportTime.text = regDate
        binding.tvReportTitle.text = titleData
        binding.tvReportContents.text = contentsData
        binding.etReportTitle.setText(titleData)
        binding.etReportContents.setText(contentsData)

        ReportService(this).getCommentList(reportId)

        Glide.with(this)
                .load(coverImgUrl)
                .into(binding.ivCoverImg)



    }

    override fun getReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun editReportSuccess(editReportResponse: EditReportResponse) {
        val intent = getIntent()
        finish();
        startActivity(intent)
    }

    override fun editCommentSuccess(editCommentResponse: EditCommentResponse) {
        TODO("Not yet implemented")
    }

    override fun postCommentSuccss(postCommentResponse: PostCommentResponse) {
        Log.d("postCommentSuccss", "postCommentSuccss!!")
        //새로고침
        val intent = getIntent()
        finish();
        startActivity(intent)
    }

    override fun getCommentSuccss(getCommentResponse: GetCommentResponse) {
        this.commentAdapter = CommentAdapter()
        for (i in getCommentResponse.result) {
            this.privateCommentArrayList.add(i)
        }
        this.commentAdapter.submitList(privateCommentArrayList)
        mRecyclerView!!.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        mRecyclerView!!.adapter = this.commentAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.report_post_tb_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu_report_modify -> {
                try {
                    //수정하면
                    // editText들 visible / textView invisible
                    // editText에 있는 애들 서버에 보내기
                    binding.etReportContents.visibility = View.VISIBLE
                    binding.etReportTitle.visibility = View.VISIBLE
                    binding.tvReportContents.visibility = View.INVISIBLE
                    binding.tvReportTitle.visibility = View.INVISIBLE
                    binding.lyComment.visibility = View.INVISIBLE
                    binding.rvComment.visibility = View.INVISIBLE
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }

            R.id.menu_report_modify_complete -> {
                try {
                    editContentsData = binding.etReportContents.text.toString()
                    editTitleData = binding.etReportTitle.text.toString()
                    ReportService(this).editReport(editTitleData!!, editContentsData!!, reportId)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}