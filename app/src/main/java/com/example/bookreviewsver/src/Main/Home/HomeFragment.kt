package com.example.bookreviewsver.src.home

import MyPageFragmentView
import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.config.MyApplication
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentHomeBinding
import com.example.bookreviewsver.src.Main.Chat.UserModel
import com.example.bookreviewsver.src.Main.Home.BestSellerAdapter
import com.example.bookreviewsver.src.Main.Home.RecommendAdapter
import com.example.bookreviewsver.src.Main.Home.SearchAdapter
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants.TAG
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver.src.Main.Home.service.HomeRetrofitManager
import com.example.bookreviewsver.src.Main.MyPage.model.*
import com.example.bookreviewsver.src.Main.SignUp.service.MyPageService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::bind,
    R.layout.fragment_home),MyPageFragmentView
{
    //어댑터
    private lateinit var bestSellerRecyclerViewAdapter : BestSellerAdapter
    private lateinit var recommendRecyclerViewAdapter : RecommendAdapter
    private lateinit var searchRecyclerViewAdapter: SearchAdapter
    private var mRecyclerView: RecyclerView? = null
    private var recommendRecyclerView: RecyclerView? = null
    var categoryIdFirst = 0;
    var userId: String? = MyApplication.sSharedPreferences.getString("userId",null)

    private val REQUEST_CODE = 1
    private var speechRecognizer: SpeechRecognizer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRecommendId.text = userId
        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        (activity as MainActivity).supportActionBar?.setCustomView(R.layout.toolbar_home)

        MyPageService(this).getLikeList()
        mRecyclerView = view.findViewById<View>(R.id.recycler_bestseller_item) as RecyclerView
        recommendRecyclerView = view.findViewById<View>(R.id.rv_recommend_book) as RecyclerView
        extractBestSeller()
        MyPageService(this).getUserInfo()
        binding.tablayoutInternal.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab?.position
                if(position == 0)
                    getBestSeller(100)
                else if(position == 1)
                    getBestSeller(101)
                else if(position == 2)
                    getBestSeller(102)
                else if(position == 3)
                    getBestSeller(103)
                else if(position == 4)
                    getBestSeller(104)
                else if(position == 5)
                    getBestSeller(105)
                else if(position == 6)
                    getBestSeller(107)
                else if(position == 7)
                    getBestSeller(108)
                else if(position == 8)
                    getBestSeller(116)
                else if(position == 9)
                    getBestSeller(117)
                else if(position == 10)
                    getBestSeller(118)
                else if(position ==11)
                    getBestSeller(119)
                else if(position ==12)
                    getBestSeller(120)
                else if(position ==13)
                    getBestSeller(122)
                else if(position ==14)
                    getBestSeller(124)
                else if(position ==15)
                    getBestSeller(109)
                else if(position ==16)
                    getBestSeller(110)
                else if(position ==17)
                    getBestSeller(112)

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.tablayoutForeign.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab?.position
                if (position == 0)
                    getBestSeller(200)
                else if (position == 1)
                    getBestSeller(201)
                else if (position == 2)
                    getBestSeller(203)
                else if (position == 3)
                    getBestSeller(205)
                else if (position == 4)
                    getBestSeller(206)
                else if (position == 5)
                    getBestSeller(207)
                else if (position == 6)
                    getBestSeller(209)
                else if (position == 7)
                    getBestSeller(210)
                else if (position == 8)
                    getBestSeller(211)
                else if (position == 9)
                    getBestSeller(214)
                else if(position ==10)
                    getBestSeller(215)
                else if(position ==11)
                    getBestSeller(216)
                else if(position ==12)
                    getBestSeller(217)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


    }

    private fun getBestSeller(categoryId : Int) {
        HomeRetrofitManager.instance.getBestSeller(
            key = BOOKAPI.CLIENT_ID,
            categoryId = categoryId,
            output = "json"
        ) { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(TAG, "getBestSeller api 호출 성공")

                    this.bestSellerRecyclerViewAdapter = BestSellerAdapter()
                    if (responseArrayList != null) {
                        this.bestSellerRecyclerViewAdapter.submitList(responseArrayList)
                    }

                    mRecyclerView!!.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
                    mRecyclerView!!.adapter = this.bestSellerRecyclerViewAdapter


                }
                RESPONSE_STATE.FAIL -> { showCustomToast("호출 에러입니다") }

            }
        }
    }

    private fun getRecommendBook(categoryId : Int) {
        HomeRetrofitManager.instance.getBestSeller(
            key = BOOKAPI.CLIENT_ID,
            categoryId = categoryId,
            output = "json"
        ) { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(Constants.TAG, "getRecommendBook api 호출 성공")

                    this.recommendRecyclerViewAdapter = RecommendAdapter()
                    if (responseArrayList != null) {
                        this.recommendRecyclerViewAdapter.submitList(responseArrayList)
                    }

                    recommendRecyclerView!!.layoutManager = LinearLayoutManager(activity,
                        LinearLayoutManager.HORIZONTAL, false)
                    recommendRecyclerView!!.adapter = this.recommendRecyclerViewAdapter


                }
                RESPONSE_STATE.FAIL -> { showCustomToast("호출 에러입니다") }

            }
        }
    }

    private fun getSearchBook(query : String) {
        HomeRetrofitManager.instance.getSearchBook(
            key = BOOKAPI.CLIENT_ID,
            query = query,
            output = "json")
        { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(TAG, "getSearchBook api 호출 성공")

                    this.searchRecyclerViewAdapter = SearchAdapter()
                    if (responseArrayList != null) {
                        this.searchRecyclerViewAdapter.submitList(responseArrayList)
                    }

                    mRecyclerView!!.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
                    mRecyclerView!!.adapter = this.searchRecyclerViewAdapter

                }
                RESPONSE_STATE.FAIL -> {
                    showCustomToast("호출 에러입니다")
                }

            }
        }
    }

    private fun extractBestSeller(){

        getBestSeller(100)

        binding.btnDomesticBook.setOnClickListener(View.OnClickListener {
            getBestSeller(100)
            binding.tablayoutForeign.visibility = View.GONE
            binding.tablayoutInternal.visibility = View.VISIBLE

        })


        binding.btnForeignBook.setOnClickListener(View.OnClickListener {
            getBestSeller(200)
            binding.tablayoutInternal.visibility = View.GONE
            binding.tablayoutForeign.visibility = View.VISIBLE
        })

        binding.btnSearch.setOnClickListener(View.OnClickListener {
            val searchContent = binding.svSearch.getQuery().toString()
            getSearchBook(searchContent)
            binding.homeTitle.visibility = View.GONE
            binding.lyMainRecommend.visibility = View.GONE
            binding.rvRecommendBook.visibility = View.GONE
            binding.tablayoutForeign.visibility = View.GONE
            binding.tablayoutInternal.visibility = View.GONE
            binding.svSearch.clearFocus()
        })

        binding.btnSearchMic.setOnClickListener(View.OnClickListener {
            startSTT()
        })
    }

    private  fun startSTT() {
        if (Build.VERSION.SDK_INT >= 23)
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO), REQUEST_CODE)

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.example.bookreviewsver")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity()).apply {
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
            binding.svSearch.setQuery(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0],false)
        }
    }

    override fun onDestroy() {

        if (speechRecognizer != null) {
            speechRecognizer!!.stopListening()
        }

        super.onDestroy()
    }

    override fun myPageSuccess(myPageResponse: MyPageResponse) {
        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        //'profiles'라는 이름의 자식 노드 참조 객체 얻어오기
        val profileRef: DatabaseReference = firebaseDatabase.getReference().child("users")
        var user = UserModel()
        user.userName = userId
        if(myPageResponse.result.userImgUrl!=null){
            user.userProfileImg = myPageResponse.result.userImgUrl
            MyApplication.sSharedPreferences.edit().putString("userProfileImg", myPageResponse.result.userImgUrl).apply() }
        else{
            user.userProfileImg = "http://www.dosan21.kr/common/img/default_profile.png"
            MyApplication.sSharedPreferences.edit().putString("userProfileImg", "http://www.dosan21.kr/common/img/default_profile.png").apply() }
        //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
        profileRef.child(userId!!).setValue(user)
    }
    override fun editMyPageSuccess(editMyPageResponse: EditMyPageResponse) {
        TODO("Not yet implemented")
    }


    override fun likeListSuccess(likeListResponse: LikeListResponse) {
        Log.d("likeListSuccess","likeListSuccess")
        if(likeListResponse.result != null) {
            Log.d("likeListSuccess", "likeListResponse존재")
            categoryIdFirst =
                likeListResponse.result[0].categoryID
            getRecommendBook(categoryIdFirst)
        }
        else{
            Log.d("likeListSuccess","likeListResponse존재안함")
            binding.tvRecommendNotice.visibility = View.VISIBLE
        }
    }

    override fun getReadListSuccess(readListResponse: ReadListResponse) {
    }

    override fun getReadingListSuccess(readingListResponse: ReadingListResponse) {
    }





}