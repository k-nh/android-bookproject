
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
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.application.config.MyApplication.Companion.sSharedPreferences
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentMypageBinding
import com.example.bookreviewsver.src.Main.Chat.MessageItem
import com.example.bookreviewsver.src.Main.Chat.User
import com.example.bookreviewsver.src.Main.MyPage.LikeListAdapter
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.LikeResult
import com.example.bookreviewsver.src.Main.MyPage.model.MyPageResponse
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService
import com.example.bookreviewsver.src.Main.SignUp.service.MyPageService
import com.example.bookreviewsver.src.report.ReportAdapter
import com.example.bookreviewsver.src.report.login.LoginMainActivity
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MyPageFragment : BaseFragment<FragmentMypageBinding>(
        FragmentMypageBinding::bind,
        R.layout.fragment_mypage
),MyPageFragmentView, ReportActivityView{
    var profileNickname : String? = null
    var userProfile: CircleImageView? = null
    var userIdx: Int = sSharedPreferences.getInt("userIdx", 0)
    var userId: String? = sSharedPreferences.getString("userId", null)
    var profileUrl: String? = sSharedPreferences.getString("profileUrl", null)
    private lateinit var reportAdapter: ReportAdapter
    private lateinit var likeListAdapter: LikeListAdapter
    private var mRecyclerView: RecyclerView? = null
    private var likeRecyclerView: RecyclerView? = null
    private var privateReportArrayList = ArrayList<Result>()
    var imgUri : Uri? = null
    var isChanged:Boolean = false //프로필을 변경한 적이 있는가?
    var profileValue:String? = null
    private val storage:FirebaseStorage  = FirebaseStorage.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyPageService(this).getUserInfo(userIdx)
        MyPageService(this).getLikeList()
        ReportService(this).getBoardList()
        mRecyclerView = binding.rvMypageBookReport
        likeRecyclerView = binding.rvMyLikeBook
        setHasOptionsMenu(true)
        userProfile = binding.ivProfile

//        //1. Firebase Database에 nickName, profileUrl을 저장
//        //firebase DB관리자 객체 소환
//        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
//        //'profiles'라는 이름의 자식 노드 참조 객체 얻어오기
//        val profileRef: DatabaseReference = firebaseDatabase.getReference("profiles")
//
//        //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
//        profileRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다.
//                val profile = snapshot?.child(userId!!)
//                for(i in profile.children){
//                    if (i.key.toString() == userId)
//                        profileValue = i.value.toString()
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("실패","Failed to read value.")
//            }
//        })

        Picasso.get().load(profileUrl).into(userProfile)


        userProfile!!.setOnClickListener {
            //프로필 이미지 선택하도록 Gallery 앱 실행
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 10)
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
                    Picasso.get().load(imgUri).into(userProfile)

                    //변경된 이미지가 있다.
                    isChanged = true
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
        val imgRef: StorageReference = storage.getReference("profileImages/$fileName")

        if(imgUri!=null) {

            //파일 업로드
            imgRef.putFile(imgUri!!)
                    .addOnSuccessListener {
                        imgRef.downloadUrl.addOnSuccessListener { uri->
                            Log.d("uri", uri.toString())
                            //파라미터로 firebase의 저장소에 저장되어 있는
                            //이미지에 대한 다운로드 주소(URL)을 문자열로 얻어오기

                            //user 생성
                            User.nickName = userId
                            User.profileUrl = uri.toString()

                            //1. Firebase Database에 nickName, profileUrl을 저장
                            //firebase DB관리자 객체 소환
                            val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
                            //'profiles'라는 이름의 자식 노드 참조 객체 얻어오기
                            val profileRef: DatabaseReference = firebaseDatabase.getReference("profiles")

                            //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
                            profileRef.child(userId!!).setValue(uri.toString())

                            //2. 내 phone에 nickName, profileUrl을 저장
                            MyApplication.sSharedPreferences.edit().putString("nickName", userId)
                                    .apply()
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
    }

    override fun myPageFailure(message: String?) {
        Log.d("로그", "myPageFailure!!!")
    }

    override fun likeListSuccess(likeListResponse: LikeListResponse) {
        Log.d("likeListSuccess", "likeListSuccess")
        this.likeListAdapter = LikeListAdapter()
        if(likeListResponse != null )
            this.likeListAdapter.submitList(likeListResponse.result as ArrayList<LikeResult>)
        likeRecyclerView!!.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
        )
        likeRecyclerView!!.adapter = this.likeListAdapter
    }

    override fun likeListFailure(message: String?) {
        Log.d("로그", "likeListFailure!!!")
    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        this.reportAdapter = ReportAdapter()
        Log.d("user id", userId!!)
        for (i in reportResponse.result) {
            if (i.id == userId) {
                Log.d("ReportListSuccess", "userId 아이디 맞는거")
                this.privateReportArrayList.add(i)
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

    override fun getCommentSuccss(getCommentResponse: GetCommentResponse) {
        TODO("Not yet implemented")
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

}