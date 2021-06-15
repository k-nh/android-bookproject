package com.example.bookreviewsver.src.report

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityReportPostBinding
import com.example.bookreviewsver.src.Main.Book.BookDetailActivity
import com.example.bookreviewsver.src.Main.Report.CommentAdapter
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService
import com.example.bookreviewsver.src.home.OtherPageActivity
import java.util.*
import kotlin.collections.ArrayList

class ReportPostActivity : BaseActivity<ActivityReportPostBinding>(ActivityReportPostBinding::inflate), ReportActivityView {
    private lateinit var commentAdapter: CommentAdapter
    private var mRecyclerView: RecyclerView? = null
    private var tts: TextToSpeech? = null
    var bookId: Int? = 0
    var booktitle: String? = null
    var writerId: String? = null
    var regDate: String? = null
    var titleData: String? = null
    var contentsData: String? = null
    var coverImgUrl: String? = null
    var profileImgUrl: String? = null
    var reportId :Int = 0
    var userProfileImg: String? = MyApplication.sSharedPreferences.getString("userProfileImg",null)
    var isbn: String? = null
    var userId: String? = MyApplication.sSharedPreferences.getString("userId", null)

    //result 변경해야함
    private var privateCommentArrayList = ArrayList<GetCommentResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbReportPost)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        reportId = intent.getIntExtra("reportId", 0)

        ReportService(this).getReport(reportId)
        mRecyclerView = binding.rvComment


        tts = TextToSpeech(this, TextToSpeech.OnInitListener {  })

        binding.btnWriteComment.setOnClickListener {
            try{
                val commentData: String = binding.etComment.text.toString()
                Log.d("댓글", userId.toString() + commentData.toString()+ userProfileImg)
                ReportService(this).postComment(reportId, commentData, userId!!,userProfileImg!!)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        binding.lyBook.setOnClickListener {
            Intent(this, BookDetailActivity::class.java).apply {
                MyApplication.sSharedPreferences.edit().putString("isbn", isbn!!)
                    .apply()
            }.run { startActivity(this) }
        }

        binding.fabReportList.setOnClickListener {
            startTTS()
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(writerId == userId) {
            menuInflater.inflate(R.menu.report_post_tb_items, menu)
        }
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_report_modify -> {
                try {
                    var intent = Intent(this, ReportWriteActivity::class.java)
                    intent.putExtra("reportId",reportId)
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }

            R.id.menu_report_delete-> {
                try {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("정말 삭제하시겠습니까?")

                    builder.setPositiveButton("확인") { dialogInterface, i ->
                        ReportService(this).deleteReport(reportId)

                        }

                    builder.setNegativeButton("취소") { dialogInterface, i ->
                            /* 취소일 때 아무 액션이 없으므로 빈칸 */
                        }
                        .show()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
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
        profileImgUrl = getReportResponse.result.profileImgUrl
        reportId = getReportResponse.result.repostId
        bookId = getReportResponse.result.bookID
        isbn = getReportResponse.result.isbn

        binding.tvReportItemName.text = writerId
        binding.tvReportItemRegdate.text = regDate
        binding.tvReportContents.text = contentsData
        binding.bookTitle.text = booktitle

        binding.tvReportItemName.setOnClickListener {
            Intent(this, OtherPageActivity::class.java).apply {
                putExtra("userId", writerId)
            }.run { startActivity(this) }
        }

        Glide.with(this)
            .load(profileImgUrl)
            .placeholder(R.drawable.default_profile)
            .into(binding.ivReportItemProfile)

        Glide.with(this)
            .load(coverImgUrl)
            .placeholder(R.drawable.ic_bottom_navigation_book)
            .into(binding.bookImg)

        ReportService(this).getCommentList(reportId)
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
        finish()
        startActivity(intent)
    }

    override fun deleteReportSuccss() {
        finish()
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

    override fun getLikeFeedListSuccss(likeFeedListResponse: LikeFeedListResponse) {
        TODO("Not yet implemented")
    }

    override fun getLikeFeedIdListSuccss(likeFeedIdListResponse: LikeFeedIdListResponse) {
        TODO("Not yet implemented")
    }

    private fun startTTS() {
        tts!!.speak(binding.tvReportContents.text.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
    }

    // TextToSpeech override 함수
    fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.KOREA)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //doSomething
            } else {
                //doSomething
            }
        } else {
            //doSomething
        }

    }

    override fun onDestroy() {

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }

}
