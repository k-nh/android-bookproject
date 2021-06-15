package com.example.bookreviewsver.src.report

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityReportWriteBinding
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver.src.Main.Home.model.SearchData
import com.example.bookreviewsver.src.Main.Home.service.HomeRetrofitManager
import com.example.bookreviewsver.src.Main.Home.service.ReportSearchAdapter
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService
import java.util.*

class ReportWriteActivity : BaseActivity<ActivityReportWriteBinding>(ActivityReportWriteBinding::inflate),ReportActivityView {
    lateinit var titleText: TextView
    lateinit var contentsText: TextView
    var bookId: Int? = 0
    var coverImgUrl :String? = null
    var bookTitle :String? = null
    var isbn: String? = null
    var userId: String? = MyApplication.sSharedPreferences.getString("userId", null)
    var userProfileImg: String? = MyApplication.sSharedPreferences.getString("userProfileImg",null)
    private lateinit var searchRecyclerViewAdapter: ReportSearchAdapter
    private var mRecyclerView: RecyclerView? = null
    private val REQUEST_CODE = 1
    private var speechRecognizer: SpeechRecognizer? = null

    var reportId :Int = 0
    var titleData: String? = null
    var contentsData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mRecyclerView = binding.rvSearchBook as RecyclerView

        setSupportActionBar(binding.tbReportWrite)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnWriteMic.setOnClickListener(View.OnClickListener {
            startSTT()
        })

        reportId = intent.getIntExtra("reportId", 0)

        if(reportId!==0){
            ReportService(this).getReport(reportId)
        }

        bookTitle = intent.getStringExtra("bookTitle")
        coverImgUrl = intent.getStringExtra("bookImgUrl")
        isbn = intent.getStringExtra("isbn")
        bookId = intent.getIntExtra("bookId", 0)


        Glide.with(this)
                .load(coverImgUrl)
                .into(binding.imgReportWriteBook)

        binding.tvBookTitle.text = bookTitle
        titleText = binding.etReportTitle as EditText
        contentsText = binding.etReportContents as EditText

        if(bookTitle == null && coverImgUrl == null && reportId == 0){
            binding.tvBookTitle.visibility = View.INVISIBLE
            binding.imgReportWriteBook.visibility = View.INVISIBLE
            binding.privateCheckbox.visibility = View.INVISIBLE
            binding.svWriteSearch.visibility = View.VISIBLE
            binding.etReportContents.visibility = View.INVISIBLE
            binding.etReportTitle.visibility = View.INVISIBLE
            binding.btnWriteSearch.visibility = View.VISIBLE
            binding.btnWriteMic.visibility = View.VISIBLE


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

                            this.searchRecyclerViewAdapter = ReportSearchAdapter()
                            if (responseArrayList != null) {
                                this.searchRecyclerViewAdapter.submitList(responseArrayList)
                            }

                            mRecyclerView!!.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
                            mRecyclerView!!.adapter = this.searchRecyclerViewAdapter

                            this.searchRecyclerViewAdapter.setItemClickListener( object : ReportSearchAdapter.ItemClickListener{
                                override fun onClick(SearchItem: SearchData) {
                                    binding.svWriteSearch.visibility = View.INVISIBLE
                                    binding.btnWriteSearch.visibility = View.INVISIBLE
                                    binding.btnWriteMic.visibility = View.INVISIBLE
                                    mRecyclerView!!.visibility = View.INVISIBLE
                                    binding.etReportTitle.visibility = View.VISIBLE
                                    binding.etReportContents.visibility = View.VISIBLE
                                    binding.tvBookTitle.visibility = View.VISIBLE
                                    binding.imgReportWriteBook.visibility = View.VISIBLE
                                    binding.privateCheckbox.visibility = View.VISIBLE
                                    bookTitle = SearchItem.title
                                    coverImgUrl = SearchItem.coverImgUrl
                                    bookId = SearchItem.itemId
                                    isbn = SearchItem.isbn
                                    binding.tvBookTitle.text = bookTitle
                                    Glide.with(this@ReportWriteActivity)
                                        .load(coverImgUrl)
                                        .into(binding.imgReportWriteBook)
                                }
                            })

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
                    val checkBox = binding.privateCheckbox

                    if (checkBox.isChecked) {
                        //데이터베이스에 데이터 저장
                        ReportService(this).postReport(
                            titleData,
                            bookId!!,
                            bookTitle!!,
                            contentsData,
                            userId!!,
                            isbn!!,
                            coverImgUrl!!,
                            userProfileImg!!,
                            true
                        )
                    }else if(reportId!==0){
                        ReportService(this).editReport(
                                titleData,
                                contentsData,
                                reportId
                        )
                    }
                    else {
                        ReportService(this).postReport(
                            titleData,
                            bookId!!,
                            bookTitle!!,
                            contentsData,
                            userId!!,
                            isbn!!,
                            coverImgUrl!!,
                            userProfileImg!!,
                            false
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }
            //뒤로가기 만들기

            else -> super.onOptionsItemSelected(item)
        }
    }

    private  fun startSTT() {
        if (Build.VERSION.SDK_INT >= 23)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO),
                REQUEST_CODE
            )

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.example.bookreviewsver")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionListener(recognitionListener())
            startListening(speechRecognizerIntent)
        }
    }

    private fun recognitionListener() = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) = showCustomToast("음성인식 시작합니다.")

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onPartialResults(partialResults: Bundle?) {}

        override fun onEvent(eventType: Int, params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            when(error) {
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> showCustomToast("퍼미션 없음")
            }
        }

        override fun onResults(results: Bundle) {
            binding.svWriteSearch.setQuery(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0],false)
        }
    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        TODO("Not yet implemented")
    }

    override fun reportListFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun postReportSuccess(postReportResponse: PostReportResponse) {
        val intent = Intent(this, ReportPostActivity::class.java)
        intent.putExtra("reportId", postReportResponse.result.repostId)
        startActivity(intent)
        finish()
    }

    override fun postReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun getReportSuccess(getReportResponse: GetReportResponse) {
        bookId = getReportResponse.result.bookID
        coverImgUrl = getReportResponse.result.coverImgUrl
        bookTitle = getReportResponse.result.bookName
        titleData = getReportResponse.result.title
        contentsData = getReportResponse.result.content

        Glide.with(this)
                .load(coverImgUrl)
                .into(binding.imgReportWriteBook)
        binding.tvBookTitle.text = bookTitle

        binding.etReportTitle.setText(titleData)
        binding.etReportContents.setText(contentsData)

    }

    override fun getReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun editReportSuccess(editReportResponse: EditReportResponse) {
        val intent = Intent(this, ReportPostActivity::class.java)
        intent.putExtra("reportId", reportId)
        startActivity(intent)
        finish()
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