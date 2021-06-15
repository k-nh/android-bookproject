
package com.example.bookreviewsver.src.home

import MyPageFragmentView
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.application.config.MyApplication.Companion.sSharedPreferences
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentMypageBinding
import com.example.bookreviewsver.src.Main.Chat.UserModel
import com.example.bookreviewsver.src.Main.MyPage.LikeListAdapter
import com.example.bookreviewsver.src.Main.MyPage.ReadListAdapter
import com.example.bookreviewsver.src.Main.MyPage.ReadingListAdapter
import com.example.bookreviewsver.src.Main.MyPage.model.*
import com.example.bookreviewsver.src.Main.MyPage.service.CheckListAdapter
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.model.Result
import com.example.bookreviewsver.src.Main.Report.service.ReportService
import com.example.bookreviewsver.src.Main.SignUp.service.MyPageService
import com.example.bookreviewsver.src.report.LikeReportAdapter
import com.example.bookreviewsver.src.report.ReportAdapter
import com.example.bookreviewsver.src.report.login.LoginMainActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MyPageFragment : BaseFragment<FragmentMypageBinding>(
        FragmentMypageBinding::bind,
        R.layout.fragment_mypage
),MyPageFragmentView, ReportActivityView{
    var profileNickname : String? = null
    var userId: String? = sSharedPreferences.getString("userId", null)
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var likeReportAdapter: LikeReportAdapter
    private lateinit var likeListAdapter: LikeListAdapter
    private lateinit var readListAdapter: ReadListAdapter
    private lateinit var readingListAdapter: ReadingListAdapter
    private var mRecyclerView: RecyclerView? = null
    private var likeRecyclerView: RecyclerView? = null
    private var privateReportArrayList = ArrayList<Result>()
    var imgUri : Uri? = null
    private val user = UserModel()
    private var likeFeedList = ArrayList<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        (activity as MainActivity).supportActionBar?.setCustomView(R.layout.toolbar_empty)

        ReportService(this).getFavoriteFeedIdList()
        MyPageService(this).getUserInfo()
        MyPageService(this).getLikeList()

        mRecyclerView = binding.rvMypageBookReport
        likeRecyclerView = binding.rvMyLikeBook
        setHasOptionsMenu(true)

        binding.tvGrade.setOnClickListener {
            binding.tvGradeNotice.visibility = View.VISIBLE
        }
        binding.tvGradeNotice.setOnClickListener {
            binding.tvGradeNotice.visibility = View.INVISIBLE
        }


        binding.ivProfile.setOnClickListener {
            //프로필 이미지 선택하도록 Gallery 앱 실행
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 10)
        }

        binding.tablayoutCheckList.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                var position = tab?.position

                if (position == 0)
                    getCheckList(0)
                else if (position == 1)
                    getCheckList(1)
                else if (position == 2)
                    getCheckList(2)
                else if (position == 3)
                    getCheckList(3)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


        binding.tablayoutFeedList.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab?.position
                if (position == 0) {
                    getLikeList(0)
                    likeFeedList.clear()
                } else if (position == 1) {
                    getLikeList(1)
                    likeFeedList.clear()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }

    private fun getCheckList(index:Int){
        if(index == 0){
            binding.tvNotice.visibility = View.GONE
            MyPageService(this).getLikeList()
        }
        else if(index == 1) {
            binding.tvNotice.visibility = View.GONE
            MyPageService(this).getReadingList()
        }
        else if(index == 2) {
            binding.tvNotice.visibility = View.GONE
            MyPageService(this).getReadList()
        }
    }


    private fun getLikeList(index:Int){
        if(index == 0)
            ReportService(this).getFavoriteFeedIdList()
        else if(index == 1) {
            ReportService(this).getFavoriteFeedList()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            10 -> {
                if (resultCode == RESULT_OK) {
                    imgUri = data!!.data
                    //Glide.with(this).load(imgUri).into(ivProfile);
                    //Glide는 이미지를 읽어와서 보여줄때 내 device의 외장메모리에 접근하는 퍼미션이 요구됨.
                    //(퍼미션이 없으면 이미지가 보이지 않음.)
                    //Glide를 사용할 때는 동적 퍼미션 필요함.

                    //Picasso 라이브러리는 퍼미션 없어도 됨.
                    Picasso.get().load(imgUri).into(binding.ivProfile)

                    saveData()

                }
            }
        }
    }

    fun saveData()
    {
        //이미지를 선택하지 않았을 수도 있으므로
        if (imgUri == null) return

        //Firebase storage에 이미지 저장하기 위해 파일명 만들기(날짜를 기반으로)
        var sdf: SimpleDateFormat = SimpleDateFormat("yyyMMddhhmmss") //20191024111224
        var fileName: String = sdf.format(Date()) + ".png"

        //Firebase storage에 저장하기
        val imgRef: StorageReference = FirebaseStorage.getInstance().getReference("profileImages/$fileName")

        if(imgUri!=null) {
            //파일 업로드
            imgRef.putFile(imgUri!!)
                .addOnSuccessListener {
                    imgRef.downloadUrl.addOnSuccessListener { uri->
                        Log.d("uri", uri.toString())
                        //파라미터로 firebase의 저장소에 저장되어 있는
                        //이미지에 대한 다운로드 주소(URL)을 문자열로 얻어오기

                        //user 생성
                        user.userName = userId
                        user.userProfileImg = uri.toString()

                        //1. Firebase Database에 nickName, profileUrl을 저장
                        //firebase DB관리자 객체 소환
                        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
                        //'profiles'라는 이름의 자식 노드 참조 객체 얻어오기
                        val profileRef: DatabaseReference = firebaseDatabase.getReference().child("users")

                        //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
                        profileRef.child(userId!!).setValue(user)

                        //2. 내 phone에 profileUrl을 저장
                        MyPageService(this).editUserInfo(uri.toString())
                        MyApplication.sSharedPreferences.edit().putString("profileUrl", uri.toString())
                            .apply()
                        //저장 완료

                    }
                }
        }
    }//saveData() ..



    override fun myPageSuccess(myPageResponse: MyPageResponse) {
        Log.d("프로필 nickname", "myPageSuccess")
        profileNickname = myPageResponse.result.id
        binding.tvProfileNickname.text = profileNickname.toString()
        binding.tvGrade.text = myPageResponse.grade
        binding.tvLikeBook.text = myPageResponse.favoriteCnt.toString()
        binding.tvReadBook.text = myPageResponse.readCnt.toString()
        binding.tvReadingBook.text = myPageResponse.readingCnt.toString()

        if(myPageResponse.result.userImgUrl== null){
            binding.ivProfile.setImageResource(R.drawable.default_profile)
        }else{
            Glide.with(this).load(myPageResponse.result.userImgUrl).into(binding.ivProfile)
        }
    }

    override fun editMyPageSuccess(editMyPageResponse: EditMyPageResponse) {
        if(editMyPageResponse.result.userImgUrl== null){
            binding.ivProfile.setImageResource(R.drawable.default_profile)
        }else{
            Glide.with(this).load(editMyPageResponse.result.userImgUrl).into(binding.ivProfile)
        }
    }


    override fun likeListSuccess(likeListResponse: LikeListResponse) {
        Log.d("likeListSuccess", "likeListSuccess")
        this.likeListAdapter = LikeListAdapter()
        if(likeListResponse.result.isEmpty()){
            binding.tvNotice.visibility = View.VISIBLE
            binding.tvNotice.text = "읽을 도서를 추가해주세요!"
        }
        if(likeListResponse.result != null )
            this.likeListAdapter.submitLikeList(likeListResponse.result as ArrayList<LikeListResult>)
        likeRecyclerView!!.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
        )
        likeRecyclerView!!.adapter = this.likeListAdapter
    }


    override fun getReadListSuccess(readListResponse: ReadListResponse) {
        Log.d("getReadListSuccess", "getReadListSuccess")
        this.readListAdapter = ReadListAdapter()
        if(readListResponse.result.isEmpty()){
            binding.tvNotice.visibility = View.VISIBLE
            binding.tvNotice.text = "읽은 도서를 추가해주세요!"
        }
        if(readListResponse != null )
            this.readListAdapter.submitReadList(readListResponse.result as ArrayList<ReadListResult>)
        likeRecyclerView!!.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        likeRecyclerView!!.adapter = this.readListAdapter
    }

    override fun getReadingListSuccess(readingListResponse: ReadingListResponse) {
        Log.d("getReadingListSuccess", "getReadingListSuccess")
        this.readingListAdapter = ReadingListAdapter()
        if(readingListResponse.result.isEmpty()){
            binding.tvNotice.visibility = View.VISIBLE
            binding.tvNotice.text = "읽고 있는 도서를 추가해주세요!"
        }
        if(readingListResponse != null )
            this.readingListAdapter.submitReadingList(readingListResponse.result as ArrayList<ReadingListResult>)
        likeRecyclerView!!.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        likeRecyclerView!!.adapter = this.readingListAdapter    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        if (reportResponse.result == null){
            showCustomToast("작성한 피드가 없습니다.")
        }
        this.reportAdapter = ReportAdapter()
        for (i in reportResponse.result) {
            if (i.id == userId) {
                if(likeFeedList.contains(i.repostId)) {
                    i.like = true
                }
                if(!privateReportArrayList.contains(i)){
                    this.privateReportArrayList.add(i)
                }
            }
        }
        this.reportAdapter.submitList(privateReportArrayList)
        mRecyclerView!!.layoutManager = GridLayoutManager(
                activity,
                1,
                GridLayoutManager.VERTICAL,
                false
        )
        mRecyclerView!!.adapter = this.reportAdapter
    }

    override fun getLikeFeedListSuccss(likeFeedListResponse: LikeFeedListResponse) {
        if (likeFeedListResponse.result.isEmpty()){
            showCustomToast("좋아요 한 피드가 없습니다.")
        }
        this.likeReportAdapter = LikeReportAdapter()
        this.likeReportAdapter.submitList(likeFeedListResponse.result)
        mRecyclerView!!.layoutManager = GridLayoutManager(
            activity, 1, GridLayoutManager.VERTICAL, false)
        mRecyclerView!!.adapter = this.likeReportAdapter
    }

    override fun getLikeFeedIdListSuccss(likeFeedIdListResponse: LikeFeedIdListResponse) {
        for (i in likeFeedIdListResponse.result) {
            likeFeedList.add(i.repostId)
            Log.d("넣기",i.repostId.toString())
        }
        ReportService(this).getBoardList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.mypage_toolbar_items, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_mypage_logout -> {
                val intent = Intent(activity, LoginMainActivity::class.java)
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


}